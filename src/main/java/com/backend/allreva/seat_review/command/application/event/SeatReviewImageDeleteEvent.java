package com.backend.allreva.seat_review.command.application.event;

import com.backend.allreva.common.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SeatReviewImageDeleteEvent extends Event {
    private final Long seatReviewId;
}
