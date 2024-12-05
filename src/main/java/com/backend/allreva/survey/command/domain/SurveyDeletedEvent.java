package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_SURVEY_DELETE;

@Getter
public class SurveyDeletedEvent extends Event {
    private final String topic = TOPIC_SURVEY_DELETE;

    private final Long surveyId;

    public SurveyDeletedEvent(
            @JsonProperty("surveyId") final Long surveyId
    ) {
        this.surveyId = surveyId;
    }
}
