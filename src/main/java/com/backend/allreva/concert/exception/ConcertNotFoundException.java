package com.backend.allreva.concert.exception;

import com.backend.allreva.common.exception.CustomException;

public class ConcertNotFoundException extends CustomException {
    public ConcertNotFoundException() {
        super(com.backend.allreva.concert.exception.ConcertErrorCode.CONCERT_NOT_FOUND);
    }

}
