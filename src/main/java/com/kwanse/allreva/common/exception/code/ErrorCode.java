package com.kwanse.allreva.common.exception.code;

public record ErrorCode(Integer status, String code, String message) {
    public static ErrorCode of(final Integer status, final String code, final String message) {
        return new ErrorCode(status, code, message);
    }
}
