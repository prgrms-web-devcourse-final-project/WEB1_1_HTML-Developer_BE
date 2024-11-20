package com.backend.allreva.auth.exception;

import com.backend.allreva.common.exception.CustomException;

public class InvalidJwtSignatureException extends CustomException {

    public InvalidJwtSignatureException() {
        super(JwtErrorCode.INVALID_JWT_SIGNATURE);
    }
}
