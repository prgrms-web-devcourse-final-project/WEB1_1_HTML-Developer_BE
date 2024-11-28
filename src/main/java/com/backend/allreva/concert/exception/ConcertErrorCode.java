package com.backend.allreva.concert.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCodeInterface {
    CONCERT_SEARCH_NOT_FOUND(HttpStatus.NO_CONTENT.value(), "CONCERT_CONCERT_SEARCH_NOT_FOUND_NOT_FOUND", "검색어에 존재하는 콘서트가 없습니다."),
    CONCERT_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CONCERT_STATUS_NOT_FOUND", "존재하지 않는 공연상태입니다."),
    CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CONCERT_NOT_FOUND", "존재하지 않는 공연입니다.");

    private final Integer status;
    private final String errorCode;
    private final String message;
    @Override
    public ErrorCode getErrorCode() {
        return null;
    }
}
