package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;

public class CustomAccessDeniedException extends CustomException {

    public CustomAccessDeniedException(final String errorMsg) {
        super(GlobalErrorCode.ACCESS_DENIED, errorMsg);
    }
}
