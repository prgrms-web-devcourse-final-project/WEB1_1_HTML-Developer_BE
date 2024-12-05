package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;

public class SurveyEventConsumingException extends CustomException {
    public SurveyEventConsumingException() {
        super(SurveyErrorCode.SURVEY_EVENT_CONSUMING_FAIL);
    }
}
