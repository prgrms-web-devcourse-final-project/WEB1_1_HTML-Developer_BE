package com.backend.allreva.surveyJoin.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyJoinEvent extends Event {

    public static final String TOPIC_SURVEY_JOIN = "survey-join";

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
