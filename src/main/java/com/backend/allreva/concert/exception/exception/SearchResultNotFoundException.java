package com.backend.allreva.concert.exception.exception;

import com.backend.allreva.common.exception.CustomException;

public class SearchResultNotFoundException extends CustomException {

    public SearchResultNotFoundException() {
        super(SearchErrorCode.SEARCH_RESULT_NOT_FOUND);
    }
}
