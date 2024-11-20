package com.backend.allreva.common.exception.code;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;


@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCodeInterface {
    BAD_REQUEST_ERROR(BAD_REQUEST.value(), BAD_REQUEST.name(), "잘못된 요청입니다."),
    NOT_SUPPORTED_URI_ERROR(NOT_FOUND.value(), NOT_FOUND.name(), "올바르지 않은 URI입니다."),
    NOT_SUPPORTED_METHOD_ERROR(METHOD_NOT_ALLOWED.value(), METHOD_NOT_ALLOWED.name(), "지원하지 않는 Method입니다."),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR(UNSUPPORTED_MEDIA_TYPE.value(), UNSUPPORTED_MEDIA_TYPE.name(), "지원하지 않는 Media type입니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.name(), "서버 에러, 관리자에게 문의해주세요."),
    ACCESS_DENIED(FORBIDDEN.value(), FORBIDDEN.name(), "올바르지 않은 권한입니다."),
    ;

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
