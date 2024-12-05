package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.event.Event;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_RENT_DELETE;

@Getter
public class RentDeleteEvent extends Event {

    private final String topic = TOPIC_RENT_DELETE;

    private final Long rentId;

    public RentDeleteEvent(
            @JsonProperty("rentId") final Long rentId
    ) {
        this.rentId = rentId;
    }
}
