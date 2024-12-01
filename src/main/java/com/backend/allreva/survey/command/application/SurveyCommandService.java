package com.backend.allreva.survey.command.application;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.survey.command.application.dto.JoinSurveyRequest;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.UpdateSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyBoardingDate;
import com.backend.allreva.survey.command.domain.SurveyJoin;
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

    public Long openSurvey(final Long memberId,
                           final OpenSurveyRequest openSurveyRequest) {
        checkConcert(openSurveyRequest);

        Survey survey = surveyCommandRepository.save(surveyConverter.toSurvey(memberId, openSurveyRequest));
        saveBoardingDates(survey, openSurveyRequest.boardingDates());

        return survey.getId();
    }

    public void updateSurvey(final Long memberId,
                             final Long surveyId,
                             final UpdateSurveyRequest request) {
        Survey survey = findSurvey(surveyId);
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

    public void removeSurvey(final Long memberId, final Long surveyId) {
        Survey survey = findSurvey(surveyId);

        if (!survey.isWriter(memberId)) {
            throw new SurveyNotWriterException();
        }

        surveyCommandRepository.delete(survey);
        surveyBoardingDateCommandRepository.deleteAllBySurvey(survey);
    }

    public Long createSurveyResponse(final Long memberId,
                                     final Long surveyId,
                                     final JoinSurveyRequest request) {
        checkSurvey(surveyId);

        SurveyJoin surveyJoin = surveyJoinCommandRepository.save(
                surveyConverter.toSurveyJoin(memberId, surveyId, request));
        return surveyJoin.getId();
    }

    private void saveBoardingDates(final Survey survey,
                                   final List<LocalDate> boardingDates) {
        boardingDates.forEach(date ->
                surveyBoardingDateCommandRepository.save(
                        new SurveyBoardingDate(survey, date)
                )
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

    private void checkSurvey(final Long surveyId) {
        if (!surveyCommandRepository.existsById(surveyId))
            throw new SurveyNotFoundException();
    }

    private Survey findSurvey(final Long surveyId) {
        return surveyCommandRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }

}
