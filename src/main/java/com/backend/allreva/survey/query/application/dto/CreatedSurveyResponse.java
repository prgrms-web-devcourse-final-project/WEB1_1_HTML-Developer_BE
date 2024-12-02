package com.backend.allreva.survey.query.application.dto;


public record CreatedSurveyResponse(SurveyResponse surveyResponse,
                                    Integer upCount,
                                    Integer downCount,
                                    Integer roundCount) {
}
