package com.backend.allreva.survey.query.application.dto;

import java.util.List;

public record SurveySearchListResponse(
        List<SurveyThumbnail> surveyThumbnails,
        List<Object> searchAfter
) {
        public static SurveySearchListResponse from(
                final List<SurveyThumbnail> surveyThumbnails,
                final List<Object> searchAfter
        ){
            return new SurveySearchListResponse(surveyThumbnails, searchAfter);
        }
}
