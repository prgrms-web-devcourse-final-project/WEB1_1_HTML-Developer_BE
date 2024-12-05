package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentJoinNotFoundException extends CustomException {

    public RentJoinNotFoundException() {
        super(RentErrorCode.RENT_JOIN_NOT_FOUND);
    }
}
