package com.backend.allreva.concert.command.domain.exception;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.member.exception.MemberErrorCode;

public class ConcertNotFoundException extends CustomException {
    public ConcertNotFoundException() {
        super(ConcertErrorCode.CONCERT_NOT_FOUND);
    }

}
