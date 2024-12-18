package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class InvalidAccessTokenException extends CustomException {
    public InvalidAccessTokenException() {
        super(JwtErrorCode.INVALID_ACCESS_TOKEN);
    }
}
