package com.kwanse.allreva.auth.exception;

import com.kwanse.allreva.common.exception.CustomException;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException() {
        super(JwtErrorCode.INVALID_JWT_TOKEN);
    }
}
