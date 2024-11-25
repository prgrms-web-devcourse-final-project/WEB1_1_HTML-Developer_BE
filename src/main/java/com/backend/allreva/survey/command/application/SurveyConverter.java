package com.backend.allreva.survey.command.application;

import com.backend.allreva.common.converter.DataConverter;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.domain.Survey;
import org.springframework.stereotype.Component;

@Component
public class SurveyConverter {
    public Survey createSurvey(Long memberId, OpenSurveyRequest request) {
        return Survey.builder()
                .memberId(memberId)
                .concertId(request.concertId())
                .title(request.title())
                .boardingDate(request.boardingDate().stream().map(DataConverter::convertToLocalDateFromDateWithDay).toList())
                .eddate(request.eddate())
                .information(request.information())
                .artistName(request.artistName())
                .region(request.region())
                .maxPassenger(request.maxPassenger())
                .build();
    }
}
