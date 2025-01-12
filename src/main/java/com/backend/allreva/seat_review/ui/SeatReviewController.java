package com.backend.allreva.seat_review.ui;

import com.backend.allreva.auth.security.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.seat_review.command.application.dto.ReviewCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat-review")
public class SeatReviewController implements SeatReviewControllerSwagger {

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Long> createSeatReview(
            @RequestBody @Valid final ReviewCreateResponse reviewCreateResponse,
            @RequestPart(required = false) final List<MultipartFile> file,
            @AuthMember final Member member) {
        return null;
    }
}
