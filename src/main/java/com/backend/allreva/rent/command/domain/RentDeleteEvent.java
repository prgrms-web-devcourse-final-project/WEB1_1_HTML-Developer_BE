package com.backend.allreva.rent.command.domain;

import com.backend.allreva.common.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentDeleteEvent extends Event {

    private Long rentId;

    public RentDeleteEvent(
            final Long rentId
    ) {
        this.rentId = rentId;
    }
}
