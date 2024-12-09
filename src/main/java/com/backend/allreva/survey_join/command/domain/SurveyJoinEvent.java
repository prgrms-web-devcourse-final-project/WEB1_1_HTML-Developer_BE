package com.backend.allreva.survey_join.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyJoinEvent extends Event {

    private Long surveyId;
    private int participationCount;

    public SurveyJoinEvent(
            final Long surveyId,
            final int participationCount
    ) {
        this.surveyId = surveyId;
        this.participationCount = participationCount;
    }
}
