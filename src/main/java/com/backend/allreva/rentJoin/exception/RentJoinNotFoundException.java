package com.backend.allreva.rentJoin.exception;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.rent.exception.RentErrorCode;

public class RentJoinNotFoundException extends CustomException {

    public RentJoinNotFoundException() {
        super(RentErrorCode.RENT_JOIN_NOT_FOUND);
    }
}
