package com.backend.allreva.survey.command.domain;

import com.backend.allreva.survey.command.application.dto.SurveyEventDto;
import com.backend.allreva.survey.command.domain.value.SurveyEventType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@JsonDeserialize(builder = SurveyEvent.SurveyEventBuilder.class)
public class SurveyEvent {
    private final Long eventId;
    private final SurveyEventDto survey;
    private final SurveyEventType eventType;
    private final LocalDateTime timestamp;

    @Builder
    @JsonCreator
    public SurveyEvent(
            @JsonProperty("eventId") Long eventId,
            @JsonProperty("survey") SurveyEventDto survey,
            @JsonProperty("eventType") SurveyEventType eventType,
            @JsonProperty("timestamp") LocalDateTime timestamp
    ) {
        this.eventId = Objects.requireNonNull(eventId);
        this.survey = Objects.requireNonNull(survey);
        this.eventType = Objects.requireNonNull(eventType);
        this.timestamp = Objects.requireNonNull(timestamp);
    }
}