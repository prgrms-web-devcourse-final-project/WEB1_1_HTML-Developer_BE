package com.backend.allreva.survey.command.application;

import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.concert.infra.ConcertJpaRepository;
import com.backend.allreva.concert.infra.dto.ConcertDateInfoResponse;
import com.backend.allreva.survey.command.application.dto.*;
import com.backend.allreva.survey.command.domain.*;
import com.backend.allreva.survey.command.domain.value.SurveyEventType;
import com.backend.allreva.survey.exception.SurveyInvalidBoardingDateException;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import com.backend.allreva.survey.infra.SurveyProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SurveyCommandService {
    private final SurveyCommandRepository surveyCommandRepository;
    private final SurveyJoinCommandRepository surveyJoinCommandRepository;
    private final SurveyBoardingDateCommandRepository surveyBoardingDateCommandRepository;
    private final ConcertJpaRepository concertRepository;
    private final SurveyConverter surveyConverter;
    private final SurveyProducer surveyProducer;
    /**
     * 수요조사 개설
     */
    public Long openSurvey(final Long memberId,
                           final OpenSurveyRequest request) {

        //가용 날짜가 콘서트 진행 날짜인지 확인
        if (!validateBoardingDates(request.concertId(), request.boardingDates())) {
            throw new SurveyInvalidBoardingDateException();
        }

        Survey survey = surveyCommandRepository.save(surveyConverter.toSurvey(memberId, request));
        saveBoardingDates(survey, request.boardingDates());

        surveyProducer.publishEvent(
                SurveyEvent.builder()
                        .eventId(survey.getId())
                        .survey(SurveyEventDto.builder()
                                .id(survey.getId())
                                .title(survey.getTitle())
                                .region(survey.getRegion())
                                .endDate(survey.getEndDate())
                                .build())
                        .eventType(SurveyEventType.CREATE)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        return survey.getId();
    }

    /**
     * 수요조사 수정
     */
    public void updateSurvey(final Long memberId,
                             final UpdateSurveyRequest request) {
        Survey survey = findSurvey(request.surveyId());

        //가용 날짜가 콘서트 진행 날짜인지 확인
        if (!validateBoardingDates(survey.getConcertId(), request.boardingDates())) {
            throw new SurveyInvalidBoardingDateException();
        }

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
     * 수요조사 삭제
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

    private boolean validateBoardingDates(Long concertId, List<LocalDate> boardingDates) {
        ConcertDateInfoResponse dateInfo = findStartDateAndEndDateById(concertId);

        LocalDate concertStartDate = dateInfo.getStartDate();
        LocalDate concertEndDate = dateInfo.getEndDate();

        for (LocalDate boardingDate : boardingDates) {
            if (boardingDate.isBefore(concertStartDate) || boardingDate.isAfter(concertEndDate)) {
                return false;
            }
        }
        return true;
    }

    private ConcertDateInfoResponse findStartDateAndEndDateById(final Long concertId) {
        return concertRepository.findStartDateAndEndDateById(concertId)
                .orElseThrow(ConcertNotFoundException::new);
    }


    private Survey findSurvey(final Long surveyId) {
        return surveyCommandRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }

}
