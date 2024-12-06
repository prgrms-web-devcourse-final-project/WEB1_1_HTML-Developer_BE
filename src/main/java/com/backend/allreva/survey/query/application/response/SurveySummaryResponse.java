package com.backend.allreva.survey.query.application.response;

import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;

public record SurveySummaryResponse(
        Long surveyId,
        String title,
        Region region,
        int participationCount,
        LocalDate endDate) {
}
