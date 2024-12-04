package com.backend.allreva.common.event;

import com.backend.allreva.common.exception.CustomException;

public class JsonParsingError extends CustomException {
    public JsonParsingError() {
        super(EventErrorCode.JSON_PARSING_ERROR);
    }
}
