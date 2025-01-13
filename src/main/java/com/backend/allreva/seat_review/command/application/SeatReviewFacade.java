package com.backend.allreva.seat_review.command.application;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.seat_review.command.application.dto.ReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class SeatReviewFacade {

    private final SeatReviewService seatReviewService;
    private final ApplicationEventPublisher eventPublisher;

    public Long createSeatReview(
            final ReviewCreateRequest request,
            final List<MultipartFile> images,
            final Member member
    ) {
        Long seatReviewId = seatReviewService.createSeatReview(request, member);

        eventPublisher.publishEvent(new SeatReviewImageEvent(seatReviewId, images));

        return seatReviewId;
    }
}
