package com.backend.allreva.seat_review.ui;

import com.backend.allreva.auth.security.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.seat_review.command.application.SeatReviewFacade;
import com.backend.allreva.seat_review.command.application.dto.ReviewCreateRequest;
import com.backend.allreva.seat_review.command.application.dto.ReviewUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat-review")
@RequiredArgsConstructor
@Slf4j
public class SeatReviewController implements SeatReviewControllerSwagger {

    private final SeatReviewFacade seatReviewFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Long> createSeatReview(
            @RequestPart @Valid final ReviewCreateRequest request,
            @RequestPart(value = "images", required = false)final List<MultipartFile> images,
            @AuthMember final Member member) {
        log.info("images : {}", images);
        return Response.onSuccess(
                seatReviewFacade.createSeatReview(request, images, member)
        );
    }

    @Override
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Long> updateSeatReview(
            @RequestPart @Valid final ReviewUpdateRequest request,
            @RequestPart(value = "images", required = false)final List<MultipartFile> images,
            @AuthMember final Member member) {
        return Response.onSuccess(
                seatReviewFacade.updateSeatReview(request, images, member)
        );
    }

    @Override
    @DeleteMapping
    public Response<Void> deleteSeatReview(
            @RequestParam final Long seatReviewId,
            @AuthMember final Member member) {
        seatReviewFacade.deleteSeatReview(seatReviewId, member);

        return Response.onSuccess();
    }


}
