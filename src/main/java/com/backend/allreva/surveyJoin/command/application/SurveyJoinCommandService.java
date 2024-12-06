package com.backend.allreva.surveyJoin.command.application;

import com.backend.allreva.surveyJoin.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.surveyJoin.command.domain.SurveyJoin;
import com.backend.allreva.surveyJoin.command.domain.SurveyJoinCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SurveyJoinCommandService {

    private final
    private final SurveyJoinCommandRepository surveyJoinCommandRepository;

    public Long createSurveyResponse(
            final Long memberId,
            final JoinSurveyRequest request
    ) {
        Survey survey = findSurvey(request.surveyId());

        //신청 가능한 날짜인지 확인
        survey.containsBoardingDate(request.boardingDate());

        SurveyJoin surveyJoin = surveyConverter.toSurveyJoin(memberId, request);
        log.info("passenger_num : {}", surveyJoin.getPassengerNum());
        return surveyJoinCommandRepository.save(surveyJoin).getId();
    }
}
