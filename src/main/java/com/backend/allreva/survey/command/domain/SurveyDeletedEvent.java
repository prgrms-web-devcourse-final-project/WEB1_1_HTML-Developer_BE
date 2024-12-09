package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyDeletedEvent extends Event {

    private Long surveyId;

    public SurveyDeletedEvent(
            final Long surveyId
    ) {
        this.surveyId = surveyId;
    }
}
