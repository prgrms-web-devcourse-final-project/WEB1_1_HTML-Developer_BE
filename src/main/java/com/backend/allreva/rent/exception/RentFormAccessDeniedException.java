package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentFormAccessDeniedException extends CustomException {

    public RentFormAccessDeniedException() {
        super(RentErrorCode.RENT_FORM_ACCESS_DENIED);
    }
}
