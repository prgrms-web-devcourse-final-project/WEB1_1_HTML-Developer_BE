package com.backend.allreva.concert.exception.search;

import com.backend.allreva.common.exception.CustomException;

public class EventPublishingException extends CustomException {
    public EventPublishingException() {
        super(SearchErrorCode.EVENT_PUBLISHING_FAIL);

    }
}
