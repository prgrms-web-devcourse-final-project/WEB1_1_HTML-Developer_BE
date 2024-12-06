package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyDeletedEvent extends Event {
    public static final String TOPIC_SURVEY_DELETE = "survey-delete";

    private Long surveyId;

    public SurveyDeletedEvent(
            final Long surveyId
    ) {
        this.surveyId = surveyId;
    }
}
