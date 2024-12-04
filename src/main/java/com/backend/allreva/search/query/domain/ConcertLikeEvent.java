package com.backend.allreva.search.query.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@JsonDeserialize(builder = ConcertLikeEvent.ConcertLikeEventBuilder.class)
public class ConcertLikeEvent {
    private final String eventId;
    private final int increaseCount;
    private final LocalDateTime timestamp;

    @Builder
    private ConcertLikeEvent(
            @JsonProperty("eventId") final String eventId,
            @JsonProperty("increaseCount") final int increaseCount,
            @JsonProperty("timestamp") final LocalDateTime timestamp) {
        this.eventId = Objects.requireNonNull(eventId, "eventId must not be null");
        this.increaseCount = increaseCount;
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
    }
}
