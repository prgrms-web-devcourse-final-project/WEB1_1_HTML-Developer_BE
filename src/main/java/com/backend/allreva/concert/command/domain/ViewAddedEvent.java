package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewAddedEvent extends Event {

    private String concertCode;
    private long viewCount;

    public ViewAddedEvent(final Concert concert) {
        this.concertCode = concert.getCode().getConcertCode();
        this.viewCount = concert.getViewCount();
    }

}
