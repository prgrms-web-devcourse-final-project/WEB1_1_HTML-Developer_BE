package com.backend.allreva.seat_review.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.seat_review.command.application.dto.ReviewCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "좌석리뷰 API", description = "좌석리뷰 API")
public interface SeatReviewControllerSwagger {

    @Operation(summary = "좌석리뷰 생성 API", description = "좌석리뷰 생성 API")
    Response<Long> createSeatReview(
            ReviewCreateResponse reviewCreateResponse,
            List<MultipartFile> images,
            Member member
    );
}
