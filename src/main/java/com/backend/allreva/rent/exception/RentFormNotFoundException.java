package com.backend.allreva.rent.exception;

import com.backend.allreva.common.exception.CustomException;

public class RentFormNotFoundException extends CustomException {

    public RentFormNotFoundException() {
        super(RentErrorCode.RENT_FORM_NOT_FOUND);
    }
}
