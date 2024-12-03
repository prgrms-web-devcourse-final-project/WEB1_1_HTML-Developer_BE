package com.backend.allreva.survey.query.application.dto;

import java.time.LocalDate;

public record SurveyBoardingDateResponse(
        LocalDate date,
        Integer participationCount
) {
}
