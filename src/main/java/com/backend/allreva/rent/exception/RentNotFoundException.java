package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentNotFoundException extends CustomException {

    public RentNotFoundException() {
        super(RentErrorCode.RENT_NOT_FOUND);
    }
}
