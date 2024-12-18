package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class InvalidRefreshTokenException extends CustomException {
    public InvalidRefreshTokenException() {
        super(JwtErrorCode.INVALID_REFRESH_TOKEN);
    }
}
