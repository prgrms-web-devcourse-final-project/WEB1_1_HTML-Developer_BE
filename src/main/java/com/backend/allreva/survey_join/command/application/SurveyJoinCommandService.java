package com.backend.allreva.survey_join.command.application;

import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyRepository;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey_join.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey_join.command.domain.SurveyJoin;
import com.backend.allreva.survey_join.command.domain.SurveyJoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyJoinCommandService {

    private final SurveyJoinConverter surveyJoinConverter;
    private final SurveyJoinRepository surveyJoinRepository;

    private final SurveyRepository surveyRepository;

    public Long createSurveyResponse(
            final Long memberId,
            final JoinSurveyRequest request
    ) {
        Survey survey = findSurvey(request.surveyId());

        //신청 가능한 날짜인지 확인
        survey.containsBoardingDate(request.boardingDate());

        SurveyJoin surveyJoin = surveyJoinConverter.toSurveyJoin(memberId, request);
        log.info("passenger_num : {}", surveyJoin.getPassengerNum());
        return surveyJoinRepository.save(surveyJoin).getId();
    }

    private Survey findSurvey(final Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }
}
