package com.backend.allreva.rent_join.exception;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.rent.exception.RentErrorCode;

public class RentJoinAccessDeniedException extends CustomException {

    public RentJoinAccessDeniedException() {
        super(RentErrorCode.RENT_JOIN_ACCESS_DENIED);
    }
}
