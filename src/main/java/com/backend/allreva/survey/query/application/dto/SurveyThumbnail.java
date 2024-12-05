package com.backend.allreva.survey.query.application.dto;

import com.backend.allreva.survey.query.application.domain.SurveyDocument;

import java.time.LocalDate;

public record SurveyThumbnail(
        String title,
        String region,
        Integer participantNum,
        LocalDate edDate
) {
        public static SurveyThumbnail from(final SurveyDocument surveyDocument) {
            return new SurveyThumbnail(
                    surveyDocument.getTitle(),
                    surveyDocument.getRegion(),
                    surveyDocument.getParticipationCount(),
                    surveyDocument.getEdDate()
            );
        }
}
