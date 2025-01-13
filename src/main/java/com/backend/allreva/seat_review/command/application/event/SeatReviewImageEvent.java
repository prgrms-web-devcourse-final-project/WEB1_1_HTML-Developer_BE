package com.backend.allreva.seat_review.command.application.event;

import com.backend.allreva.common.event.Event;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class SeatReviewImageEvent extends Event {
    private final Long seatReviewId;
    private final List<MultipartFile> images;

    public SeatReviewImageEvent(Long seatReviewId, List<MultipartFile> images) {
        super();
        this.seatReviewId = seatReviewId;
        this.images = images;
    }
}
