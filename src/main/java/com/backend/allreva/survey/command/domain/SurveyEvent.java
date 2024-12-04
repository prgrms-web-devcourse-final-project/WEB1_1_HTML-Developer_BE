package com.backend.allreva.survey.command.domain;

import com.backend.allreva.survey.command.domain.value.SurveyEventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@JsonDeserialize(builder =
SurveyEvent.SurveyEventBuilder.class)
public class SurveyEvent {
    private final Long eventId;
    private final Survey survey;
    private final SurveyEventType eventType;
    private final LocalDateTime timestamp;


    @Builder
    private SurveyEvent(
            @JsonProperty("eventId") final Long eventId,
            @JsonProperty("survey") final Survey survey,
            @JsonProperty("surveyEventType") final SurveyEventType eventType,
            @JsonProperty("timestamp") final LocalDateTime timestamp
    ) {
        this.eventId = Objects.requireNonNull(eventId, "eventId cannot be null");
        this.survey = Objects.requireNonNull(survey, "survey cannot be null");
        this.eventType = Objects.requireNonNull(eventType, "eventType cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp cannot be null");
    }
}
