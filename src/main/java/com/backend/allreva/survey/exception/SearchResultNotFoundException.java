package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.concert.exception.exception.SearchErrorCode;

public class SearchResultNotFoundException extends CustomException {
    public SearchResultNotFoundException() {
        super(SearchErrorCode.SEARCH_RESULT_NOT_FOUND);
    }
}
