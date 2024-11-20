package com.backend.allreva.auth.exception;

import com.backend.allreva.common.exception.CustomException;

public class ExpiredJwtTokenException extends CustomException {

    public ExpiredJwtTokenException() {
        super(JwtErrorCode.EXPIRED_JWT_TOKEN);
    }
}
