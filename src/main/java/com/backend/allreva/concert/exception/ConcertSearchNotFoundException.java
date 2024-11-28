package com.backend.allreva.concert.exception;

import com.backend.allreva.common.exception.CustomException;

public class ConcertSearchNotFoundException extends CustomException {
    public ConcertSearchNotFoundException() {
        super(ConcertErrorCode.CONCERT_NOT_FOUND);
    }
}
