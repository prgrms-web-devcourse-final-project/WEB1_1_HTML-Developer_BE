package com.backend.allreva.concert.exception.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SearchErrorCode implements ErrorCodeInterface {
    ELASTICSEARCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ELASTICSEARCH_ERROR", "elasticsearch 서버 오류입니다."),
    SEARCH_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "SEARCH_RESULT_NOT_FOUND", "검색어에 매칭 되는 결과가 없습니다."),
    EVENT_PUBLISHING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EVENT_PUBLISHING_FAIL", "이벤트 발행 실패"),
    EVENT_CONSUMING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EVENT_CONSUMING_FAIL", "이벤트 소비 실패"),
    ;
    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
