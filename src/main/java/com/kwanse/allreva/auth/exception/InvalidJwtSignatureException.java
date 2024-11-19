package com.kwanse.allreva.auth.exception;

import com.kwanse.allreva.common.exception.CustomException;

public class InvalidJwtSignatureException extends CustomException {

    public InvalidJwtSignatureException() {
        super(JwtErrorCode.INVALID_JWT_SIGNATURE);
    }
}
