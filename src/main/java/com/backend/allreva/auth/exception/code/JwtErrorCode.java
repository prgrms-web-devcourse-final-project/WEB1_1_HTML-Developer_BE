package com.backend.allreva.auth.exception.code;

import org.springframework.http.HttpStatus;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCodeInterface {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다. 재 로그인 해주세요."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_ACCESS_TOKEN", "유효하지 않은 액세스 토큰입니다. 재발급 해주세요."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "TOKEN_NOT_FOUND", "토큰이 존재하지 않습니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
