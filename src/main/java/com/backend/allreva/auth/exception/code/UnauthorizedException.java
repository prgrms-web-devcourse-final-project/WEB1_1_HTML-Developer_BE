package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String errorMsg) {
        super(GlobalErrorCode.UNAUTHORIZED_ERROR, errorMsg);
    }
}
