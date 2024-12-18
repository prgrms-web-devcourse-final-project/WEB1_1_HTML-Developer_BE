package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException() {
        super(JwtErrorCode.INVALID_TOKEN);
    }
}
