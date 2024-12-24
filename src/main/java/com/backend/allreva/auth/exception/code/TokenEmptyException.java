package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class TokenEmptyException extends CustomException {
    public TokenEmptyException() {
        super(JwtErrorCode.TOKEN_EMPTY);
    }
}
