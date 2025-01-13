package com.backend.allreva.seat_review.ui;

import com.backend.allreva.auth.security.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.seat_review.command.application.SeatReviewFacade;
import com.backend.allreva.seat_review.command.application.dto.ReviewCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat-review")
@RequiredArgsConstructor
public class SeatReviewController implements SeatReviewControllerSwagger {

    private final SeatReviewFacade seatReviewFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Long> createSeatReview(
            @RequestPart @Valid final ReviewCreateRequest request,
            @RequestPart(required = false) final List<MultipartFile> Images,
            @AuthMember final Member member) {
        return Response.onSuccess(
                seatReviewFacade.createSeatReview(request, Images, member)
        );
    }
}
