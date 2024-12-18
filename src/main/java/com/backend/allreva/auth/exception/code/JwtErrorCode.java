package com.backend.allreva.auth.exception.code;

import org.springframework.http.HttpStatus;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCodeInterface {

    TOKEN_INVALID(HttpStatus.UNAUTHORIZED.value(), "TOKEN_INVALID", "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "TOKEN_NOT_FOUND", "토큰을 찾을 수 없습니다."),
    TOKEN_NOT_MATCH(HttpStatus.UNAUTHORIZED.value(), "TOKEN_NOT_MATCH", "서버에 저장된 토큰과 일치하지 않습니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
