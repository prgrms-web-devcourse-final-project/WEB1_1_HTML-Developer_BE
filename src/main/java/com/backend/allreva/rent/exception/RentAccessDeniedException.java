package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentAccessDeniedException extends CustomException {

    public RentAccessDeniedException() {
        super(RentErrorCode.RENT_ACCESS_DENIED);
    }
}
