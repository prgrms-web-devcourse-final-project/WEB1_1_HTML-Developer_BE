package com.backend.allreva.search.exception;

import com.backend.allreva.common.exception.CustomException;

public class ElasticSearchException extends CustomException {
    public ElasticSearchException() {
        super(SearchErrorCode.ELASTICSEARCH_ERROR);
    }
}
