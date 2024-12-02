package com.backend.allreva.diary.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode implements ErrorCodeInterface {

    DIARY_NOT_WRITER(HttpStatus.FORBIDDEN.value(), "DIARY_NOT_WRITER", "삭제할 권한이 없습니다"),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "DIARY_NOT_FOUND", "존재하지 않는 공연 기록입니다");

    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
