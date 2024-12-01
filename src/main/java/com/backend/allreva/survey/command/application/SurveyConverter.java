package com.backend.allreva.survey.command.application;

import com.backend.allreva.survey.command.application.dto.JoinSurveyRequest;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.command.domain.SurveyJoin;
import org.springframework.stereotype.Component;

@Component
public class SurveyConverter {
    public Survey toSurvey(final Long memberId,
                           final OpenSurveyRequest request) {
        return Survey.builder()
                .memberId(memberId)
                .concertId(request.concertId())
                .title(request.title())
                .endDate(request.endDate())
                .information(request.information())
                .artistName(request.artistName())
                .region(request.region())
                .maxPassenger(request.maxPassenger())
                .build();
    }

    public SurveyJoin toSurveyJoin(final Long memberId,
                                   final Long surveyId,
                                   final JoinSurveyRequest request) {
        return SurveyJoin.builder()
                .memberId(memberId)
                .surveyId(surveyId)
                .boardingDate(request.boardingDate())
                .boardingType(request.boardingType())
                .passengerNum(request.passengerNum())
                .notified(request.notified())
                .build();
    }

}
