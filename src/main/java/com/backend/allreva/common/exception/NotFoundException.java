package com.backend.allreva.common.exception;

import com.backend.allreva.common.exception.code.GlobalErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException() {
      super(GlobalErrorCode.NOT_FOUND_DATA);
    }
}
