package com.backend.allreva.concert.exception.search;

import com.backend.allreva.common.exception.CustomException;

public class SearchResultNotFoundException extends CustomException {

    public SearchResultNotFoundException() {
        super(SearchErrorCode.SEARCH_RESULT_NOT_FOUND);
    }
}
