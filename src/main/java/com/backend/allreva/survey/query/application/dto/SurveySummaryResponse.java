package com.backend.allreva.survey.query.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;


public final class SurveySummaryResponse {
    final Long surveyId;
    final String title;
    final Region region;
    final Long participationCount;
    final LocalDate endDate;

    public SurveySummaryResponse(Long surveyId, String title, Region region, Long participationCount, LocalDate endDate) {
        this.surveyId = surveyId;
        this.title = title;
        this.region = region;
        this.participationCount = participationCount;
        this.endDate = endDate;
    }
}
