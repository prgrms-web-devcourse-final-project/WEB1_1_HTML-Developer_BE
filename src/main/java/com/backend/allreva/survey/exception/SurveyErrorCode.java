package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.code.ErrorCode;
import com.backend.allreva.common.exception.code.ErrorCodeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SurveyErrorCode implements ErrorCodeInterface {
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "SURVEY_NOT_FOUND", "존재하지 않는 수요조사입니다."),
    SURVEY_NOT_WRITER(HttpStatus.FORBIDDEN.value(), "SURVEY_NOT_WRITER", "작성자가 아니므로 권한이 없습니다."),
    SURVEY_ILLEGAL_PARAMETER(HttpStatus.BAD_REQUEST.value(), "SURVEY_ILLEGAL_PARAMETER", "lastEndDate는 lastId와 함께 제공되어야합니다."),
    SURVEY_INVALID_BOARDING_DATE(HttpStatus.BAD_REQUEST.value(), "SURVEY_INVALID_BOARDING_DATE", "가용 날짜가 아닙니다."),
    SURVEY_EVENT_PUBLISHING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SURVEY_EVENT_PUBLISHING_FAIL", "survey event 발행 실패"),
    SURVEY_EVENT_CONSUMING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SURVEY_EVENT_CONSUMING_FAIL", "survey event 소비 실패");
    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.of(status, errorCode, message);
    }
}
