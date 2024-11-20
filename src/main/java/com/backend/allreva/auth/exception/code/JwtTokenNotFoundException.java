package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class JwtTokenNotFoundException extends CustomException {

    public JwtTokenNotFoundException() {
        super(JwtErrorCode.JWT_TOKEN_NOT_FOUND);
    }
}
