package com.backend.allreva.survey.query.application.response;

import java.time.LocalDate;

public record SurveyBoardingDateResponse(
        LocalDate date,
        int participationCount
) {
}
