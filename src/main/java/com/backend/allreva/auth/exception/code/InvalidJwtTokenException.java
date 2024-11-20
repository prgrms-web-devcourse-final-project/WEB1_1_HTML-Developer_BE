package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException() {
        super(JwtErrorCode.INVALID_JWT_TOKEN);
    }
}
