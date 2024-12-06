package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveySavedEvent extends Event {

    public static final String TOPIC_SURVEY_SAVE = "survey-save";

    private Long surveyId;
    private String title;
    private Region region;
    private LocalDate endDate;

    public SurveySavedEvent(final Survey survey) {
        this.surveyId = survey.getId();
        this.title = survey.getTitle();
        this.region = survey.getRegion();
        this.endDate = survey.getEndDate();
    }

    public SurveyDocument to() {
        return SurveyDocument.builder()
                .id(surveyId.toString())
                .title(title)
                .region(region.name())
                .edDate(endDate)
                .participationCount(0)
                .build();
    }
}