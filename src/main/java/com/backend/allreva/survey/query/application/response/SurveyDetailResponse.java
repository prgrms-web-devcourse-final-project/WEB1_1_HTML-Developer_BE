package com.backend.allreva.survey.query.application.response;

import java.util.List;

public record SurveyDetailResponse(Long surveyId,
                                   String title,
                                   List<SurveyBoardingDateResponse> boardingDates,
                                   String information,
                                   boolean isClosed) {
}
