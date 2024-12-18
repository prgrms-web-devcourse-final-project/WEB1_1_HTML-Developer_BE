package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class TokenNotMatchException extends CustomException {
    public TokenNotMatchException() {
        super(JwtErrorCode.TOKEN_NOT_MATCH);
    }
}
