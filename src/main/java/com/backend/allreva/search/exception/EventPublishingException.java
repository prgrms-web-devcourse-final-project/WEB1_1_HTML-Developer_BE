package com.backend.allreva.search.exception;

import com.backend.allreva.common.exception.CustomException;

public class EventPublishingException extends CustomException {
    public EventPublishingException() {
        super(SearchErrorCode.EVENT_PUBLISHING_FAIL);

    }
}
