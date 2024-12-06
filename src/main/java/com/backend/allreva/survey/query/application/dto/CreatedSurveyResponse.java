package com.backend.allreva.survey.query.application.dto;


public record CreatedSurveyResponse(SurveyResponse surveyResponse,
                                    int upCount,
                                    int downCount,
                                    int roundCount) {
}
