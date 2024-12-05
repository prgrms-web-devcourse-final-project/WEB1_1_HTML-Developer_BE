package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_SURVEY_JOIN;

@Getter
public class SurveyJoinEvent extends Event {

    private final String topic = TOPIC_SURVEY_JOIN;

    private final Long surveyId;
    private final int participationCount;

    public SurveyJoinEvent(
            @JsonProperty("surveyId") Long surveyId,
            @JsonProperty("participationCount") int participationCount
    ) {
        this.surveyId = surveyId;
        this.participationCount = participationCount;
    }
}
