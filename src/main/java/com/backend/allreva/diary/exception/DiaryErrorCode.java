package com.backend.allreva.diary.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum DiaryErrorCode implements ErrorCodeInterface {

    DIARY_NOT_WRITER(HttpStatus.FORBIDDEN.value(), "DIARY_NOT_WRITER", "삭제할 권한이 없습니다"),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "DIARY_NOT_FOUND", "삭제할 권한이 없습니다");

    private final Integer status;
    private final String errorCode;
    private final String message;

    DiaryErrorCode(Integer status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return null;
    }
}
