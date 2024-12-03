package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentJoinAccessDeniedException extends CustomException {

    public RentJoinAccessDeniedException() {
        super(RentErrorCode.RENT_JOIN_ACCESS_DENIED);
    }
}
