package com.backend.allreva.survey.command.application;

import com.backend.allreva.survey.command.application.request.OpenSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
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

}
