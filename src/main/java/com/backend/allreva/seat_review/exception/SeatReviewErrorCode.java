package com.backend.allreva.seat_review.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SeatReviewErrorCode implements ErrorCodeInterface {
    SEAT_REVIEW_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SEAT_REVIEW_SAVE_FALIED", "좌석리뷰 저장 실패"),
    SEAT_REVIEW_IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SEAT_REVIEW_IMAGE_SAVE_FAILED", "좌석리뷰 이미지 저장 실패");

    private final Integer status;
    private final String errorCode;
    private final String message;


    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }

}
