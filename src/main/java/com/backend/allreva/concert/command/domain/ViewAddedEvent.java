package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ViewAddedEvent extends Event {

    private final String topic;
    private final String concertCode;
    private final long viewCount;

    @Builder
    private ViewAddedEvent(
            final String concertCode,
            final long viewCount
    ) {
        this.topic = "concertLike-event";
        this.concertCode = concertCode;
        this.viewCount = viewCount;
    }

}
