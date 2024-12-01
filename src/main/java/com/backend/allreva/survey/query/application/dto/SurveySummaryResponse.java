package com.backend.allreva.survey.query.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;

public record SurveySummaryResponse(
        Long surveyId,
        String title,
        Region region,
        Integer participationCount,
        Integer maxPassenger,
        LocalDate endDate) {
}
