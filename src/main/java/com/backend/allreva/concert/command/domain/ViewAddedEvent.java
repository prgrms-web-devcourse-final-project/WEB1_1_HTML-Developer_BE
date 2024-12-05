package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_CONCERT_VIEW;

@Getter
@NoArgsConstructor
public class ViewAddedEvent extends Event {

    private final String topic = TOPIC_CONCERT_VIEW;
    private String concertCode;
    private long viewCount;

    public ViewAddedEvent(final Concert concert) {
        this.concertCode = concert.getCode().getConcertCode();
        this.viewCount = concert.getViewCount();
    }

}
