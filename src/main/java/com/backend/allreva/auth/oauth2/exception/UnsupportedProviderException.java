package com.backend.allreva.auth.oauth2.exception;

import com.backend.allreva.common.exception.CustomException;

public class UnsupportedProviderException extends CustomException {
    public UnsupportedProviderException() {
        super(OAuth2ErrorCode.UNSUPPORTED_PROVIDER);
    }
}
