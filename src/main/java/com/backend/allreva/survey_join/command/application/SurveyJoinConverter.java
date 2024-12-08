package com.backend.allreva.survey_join.command.application;

import com.backend.allreva.survey_join.command.application.request.JoinSurveyRequest;
import com.backend.allreva.survey_join.command.domain.SurveyJoin;
import org.springframework.stereotype.Component;

@Component
public class SurveyJoinConverter {

    public SurveyJoin toSurveyJoin(final Long memberId,
                                   final JoinSurveyRequest request) {
        return SurveyJoin.builder()
                .memberId(memberId)
                .surveyId(request.surveyId())
                .boardingDate(request.boardingDate())
                .boardingType(request.boardingType())
                .passengerNum(request.passengerNum())
                .notified(request.notified())
                .build();
    }
}
