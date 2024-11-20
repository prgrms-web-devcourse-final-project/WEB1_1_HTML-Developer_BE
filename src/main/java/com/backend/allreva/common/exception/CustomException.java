package com.backend.allreva.common.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCodeInterface errorCode;

    public ErrorCode getErrorCode() {
        return this.errorCode.getErrorCode();
    }
}
