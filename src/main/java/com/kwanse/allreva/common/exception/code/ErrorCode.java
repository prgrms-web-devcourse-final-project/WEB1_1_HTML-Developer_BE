package com.kwanse.allreva.common.exception.code;

public record ErrorCode(Integer status, String code, String message) {
    public static ErrorCode of(Integer status, String code, String message) {
        return new ErrorCode(status, code, message);
    }
}
