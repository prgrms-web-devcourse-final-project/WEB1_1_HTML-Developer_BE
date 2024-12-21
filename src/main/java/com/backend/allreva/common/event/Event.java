package com.backend.allreva.common.event;

import com.backend.allreva.concert.command.domain.ViewAddedEvent;
import com.backend.allreva.rent.command.domain.RentDeletedEvent;
import com.backend.allreva.rent.command.domain.RentSaveEvent;
import com.backend.allreva.survey.command.domain.SurveyDeletedEvent;
import com.backend.allreva.survey.command.domain.SurveySavedEvent;
import com.backend.allreva.survey_join.command.domain.SurveyJoinEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ViewAddedEvent.class, name = "VIEW_ADDED"),
        @JsonSubTypes.Type(value = SurveySavedEvent.class, name = "SURVEY_SAVED"),
        @JsonSubTypes.Type(value = SurveyDeletedEvent.class, name = "SURVEY_DELETED"),
        @JsonSubTypes.Type(value = SurveyJoinEvent.class, name = "SURVEY_JOINED"),
        @JsonSubTypes.Type(value = RentSaveEvent.class, name = "RENT_SAVED"),
        @JsonSubTypes.Type(value = RentDeletedEvent.class, name = "RENT_DELETED")
})
public abstract class Event {

    private final long timestamp;
    private boolean isReissued;

    protected Event() {
        this.timestamp = System.currentTimeMillis();
        this.isReissued = false;
    }

    public void markAsReissued() {
        this.isReissued = true;
    }
}
