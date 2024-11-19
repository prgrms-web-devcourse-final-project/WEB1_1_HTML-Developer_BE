package com.kwanse.allreva.auth.exception;

import com.kwanse.allreva.common.exception.CustomException;

public class ExpiredJwtTokenException extends CustomException {

    public ExpiredJwtTokenException() {
        super(JwtErrorCode.EXPIRED_JWT_TOKEN);
    }
}
