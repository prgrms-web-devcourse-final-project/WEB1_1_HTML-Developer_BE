package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.domain.SurveyDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_SURVEY_SAVE;

@Getter
public class SurveySavedEvent extends Event {

    private final String topic = TOPIC_SURVEY_SAVE;

    private final Long surveyId;
    private final String title;
    private final Region region;
    private final LocalDate endDate;


    @Builder
    private SurveySavedEvent(
            @JsonProperty("surveyId") final Long surveyId,
            @JsonProperty("title") final String title,
            @JsonProperty("region") final Region region,
            @JsonProperty("endDate") final LocalDate endDate
    ) {
        this.surveyId = surveyId;
        this.title = title;
        this.region = region;
        this.endDate = endDate;
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