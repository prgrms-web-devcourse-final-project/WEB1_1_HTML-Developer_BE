package com.backend.allreva.auth.exception.code;

import com.backend.allreva.common.exception.CustomException;

public class UnsupportedProviderException extends CustomException {
    public UnsupportedProviderException() {
        super(OAuth2ErrorCode.UNSUPPORTED_PROVIDER);
    }
}
