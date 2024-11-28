package com.backend.allreva.concert.exception;

import com.backend.allreva.common.exception.CustomException;

public class ConcertStatusNotFoundException extends CustomException {
    public ConcertStatusNotFoundException() {
        super(ConcertErrorCode.CONCERT_STATUS_NOT_FOUND);
    }
}
