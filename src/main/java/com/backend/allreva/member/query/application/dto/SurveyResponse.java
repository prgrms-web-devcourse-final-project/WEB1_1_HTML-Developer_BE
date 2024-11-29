package com.backend.allreva.member.query.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public final class SurveyResponse {
    private final Long surveyId;
    private final String title;
    private final LocalDate boardingDate;
    private final Region region;
    private final LocalDate surveyStartDate;
    private final LocalDate surveyEndDate;
    private final Integer participationCount;
    private final Integer maxPassenger;

    public SurveyResponse(final Long surveyId,
                          final String title,
                          final LocalDate boardingDate,
                          final Region region,
                          final LocalDateTime surveyStartDate,
                          final LocalDate surveyEndDate,
                          final Integer participationCount,
                          final Integer maxPassenger) {
        this.surveyId = surveyId;
        this.title = title;
        this.boardingDate = boardingDate;
        this.region = region;
        this.surveyStartDate = surveyStartDate.toLocalDate();
        this.surveyEndDate = surveyEndDate;
        this.participationCount = participationCount;
        this.maxPassenger = maxPassenger;
    }
}
