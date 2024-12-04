package com.backend.allreva.survey.command.domain;

import com.backend.allreva.survey.command.application.dto.SurveyEventDto;
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
    @JsonProperty("eventId")
    private final Long eventId;
    @JsonProperty("survey")
    private final SurveyEventDto survey;
    @JsonProperty("eventType")
    private final SurveyEventType eventType;
    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;



    @Builder
    private SurveyEvent(
            @JsonProperty("eventId") final Long eventId,
            @JsonProperty("survey") SurveyEventDto survey,
            @JsonProperty("surveyEventType") final SurveyEventType eventType,
            @JsonProperty("timestamp") final LocalDateTime timestamp
    ) {
        this.eventId = Objects.requireNonNull(eventId, "eventId cannot be null");
        this.survey = Objects.requireNonNull(survey, "survey cannot be null");
        this.eventType = Objects.requireNonNull(eventType, "eventType cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp cannot be null");
    }
}
