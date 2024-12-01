package com.backend.allreva.survey.command.application;

import com.backend.allreva.common.converter.DataConverter;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.survey.command.application.dto.*;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyBoardingDate;
import com.backend.allreva.survey.command.domain.SurveyJoin;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public SurveyIdResponse openSurvey(final Long memberId,
                                       final OpenSurveyRequest openSurveyRequest) {
        checkConcert(openSurveyRequest);

        Survey survey = surveyCommandRepository.save(surveyConverter.toSurvey(memberId, openSurveyRequest));
        saveBoardingDates(survey, openSurveyRequest.boardingDates());

        return new SurveyIdResponse(survey.getId());
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
                request.eddate(),
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

    public SurveyJoinIdResponse createSurveyResponse(final Long memberId,
                                                     final Long surveyId,
                                                     final JoinSurveyRequest request) {
        checkSurvey(surveyId);

        SurveyJoin surveyJoin = surveyJoinCommandRepository.save(
                surveyConverter.toSurveyJoin(memberId, surveyId, request));
        return new SurveyJoinIdResponse(surveyJoin.getId());
    }

    private void saveBoardingDates(final Survey survey,
                                   final List<String> boardingDates) {
        boardingDates.forEach(date ->
                surveyBoardingDateCommandRepository.save(
                        new SurveyBoardingDate(survey, DataConverter.convertToLocalDateFromDateWithDay(date))
                )
        );
    }

    private void updateBoardingDates(final Survey survey,
                                     final List<String> boardingDates) {
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
