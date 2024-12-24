package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class TokenInvalidException extends CustomException {

    public TokenInvalidException() {
        super(JwtErrorCode.TOKEN_INVALID);
    }
}
