package com.backend.allreva.survey.query.application.response;


public record CreatedSurveyResponse(SurveyResponse surveyResponse,
                                    int upCount,
                                    int downCount,
                                    int roundCount) {
}
