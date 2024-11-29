package com.backend.allreva.member.query.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class SurveyResponse {
    Long surveyId;
    String title;
    LocalDate boardingDate;
    Region region;
    LocalDate surveyStartDate;
    LocalDate surveyEndDate;
    int participationCount;
    int maxPassenger;

    public SurveyResponse(Long surveyId, String title, LocalDate boardingDate, Region region, LocalDateTime surveyStartDate, LocalDate surveyEndDate, int participationCount, int maxPassenger) {
        this.surveyId = surveyId;
        this.title = title;
        this.boardingDate = boardingDate;
        this.region = region;
        this.surveyStartDate = surveyStartDate.toLocalDate();
        this.surveyEndDate = surveyEndDate;
        this.participationCount = participationCount;
        this.maxPassenger = maxPassenger;
    }

    @Override
    public String toString() {
        return "SurveyResponse{" +
                "surveyId=" + surveyId +
                ", title='" + title + '\'' +
                ", boardingDate=" + boardingDate +
                ", region=" + region +
                ", surveyStartDate=" + surveyStartDate +
                ", surveyEndDate=" + surveyEndDate +
                ", participationCount=" + participationCount +
                ", maxPassenger=" + maxPassenger +
                '}';
    }
}
