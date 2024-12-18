package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class TokenNotFoundException extends CustomException {

    public TokenNotFoundException() {
        super(JwtErrorCode.TOKEN_NOT_FOUND);
    }
}
