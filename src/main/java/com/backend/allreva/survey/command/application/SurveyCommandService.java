package com.backend.allreva.survey.command.application;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.survey.command.application.dto.JoinSurveyRequest;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdRequest;
import com.backend.allreva.survey.command.application.dto.UpdateSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyBoardingDate;
import com.backend.allreva.survey.command.domain.SurveyJoin;
import com.backend.allreva.survey.exception.SurveyInvalidBoardingDateException;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyCommandService {
    private final SurveyCommandRepository surveyCommandRepository;
    private final SurveyJoinCommandRepository surveyJoinCommandRepository;
    private final SurveyBoardingDateCommandRepository surveyBoardingDateCommandRepository;
    private final ConcertRepository concertRepository;
    private final SurveyConverter surveyConverter;

    /**
     * 수요조사 개설
     */
    public Long openSurvey(final Long memberId,
                           final OpenSurveyRequest openSurveyRequest) {
        checkConcert(openSurveyRequest);

        Survey survey = surveyCommandRepository.save(surveyConverter.toSurvey(memberId, openSurveyRequest));
        saveBoardingDates(survey, openSurveyRequest.boardingDates());

        return survey.getId();
    }

    /**
     * 수요조사 수정
     */
    public void updateSurvey(final Long memberId,
                             final UpdateSurveyRequest request) {
        Survey survey = findSurvey(request.surveyId());
        if (!survey.isWriter(memberId)) {
            throw new SurveyNotWriterException();
        }

        survey.update(request.title(),
                request.region(),
                request.endDate(),
                request.maxPassenger(),
                request.information()
        );

        updateBoardingDates(survey, request.boardingDates());
    }

    /**
     * 수요조사
     */
    public void removeSurvey(final Long memberId, final SurveyIdRequest surveyIdRequest) {
        Survey survey = findSurvey(surveyIdRequest.surveyId());

        if (!survey.isWriter(memberId)) {
            throw new SurveyNotWriterException();
        }

        surveyCommandRepository.delete(survey);
        surveyBoardingDateCommandRepository.deleteAllBySurvey(survey);
    }

    /**
     * 수요조사 응답(신청)
     */
    public Long createSurveyResponse(final Long memberId,
                                     final JoinSurveyRequest request) {
        Survey survey = findSurvey(request.surveyId());

        if (!survey.containsBoardingDate(request.boardingDate())) {
            throw new SurveyInvalidBoardingDateException();
        }

        SurveyJoin surveyJoin = surveyJoinCommandRepository.save(
                surveyConverter.toSurveyJoin(memberId, request));
        return surveyJoin.getId();
    }

    private void saveBoardingDates(final Survey survey,
                                   final List<LocalDate> boardingDates) {
        boardingDates.forEach(date ->
                surveyBoardingDateCommandRepository.save(
                        SurveyBoardingDate.builder()
                                .survey(survey)
                                .date(date)
                                .build())
        );
    }

    private void updateBoardingDates(final Survey survey,
                                     final List<LocalDate> boardingDates) {
        surveyBoardingDateCommandRepository.deleteAllBySurveyForUpdate(survey);
        saveBoardingDates(survey, boardingDates);
    }

    private void checkConcert(final OpenSurveyRequest request) {
        if (!concertRepository.existsById(request.concertId()))
            throw new ConcertNotFoundException();
    }

    private Survey findSurvey(final Long surveyId) {
        return surveyCommandRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }

}
