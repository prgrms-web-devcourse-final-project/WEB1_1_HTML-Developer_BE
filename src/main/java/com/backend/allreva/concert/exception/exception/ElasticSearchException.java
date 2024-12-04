package com.backend.allreva.concert.exception.exception;

import com.backend.allreva.common.exception.CustomException;

public class ElasticSearchException extends CustomException {
    public ElasticSearchException() {
        super(SearchErrorCode.ELASTICSEARCH_ERROR);
    }
}
