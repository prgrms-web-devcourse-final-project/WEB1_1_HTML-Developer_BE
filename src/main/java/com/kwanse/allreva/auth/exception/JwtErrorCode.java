package com.kwanse.allreva.auth.exception;

import org.springframework.http.HttpStatus;

import com.kwanse.allreva.common.exception.code.ErrorCode;
import com.kwanse.allreva.common.exception.code.ErrorCodeInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCodeInterface {

    INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST.value(), "INVALID_JWT_TOKEN", "유효하지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.BAD_REQUEST.value(), "INVALID_JWT_SIGNATURE", "유효하지 않은 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.BAD_REQUEST.value(), "EXPIRED_JWT_TOKEN", "토큰이 만료되었습니다.")
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
