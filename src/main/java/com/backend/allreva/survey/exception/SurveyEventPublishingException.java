package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;

public class SurveyEventPublishingException extends CustomException {
    public SurveyEventPublishingException() {
        super(SurveyErrorCode.SURVEY_EVENT_PUBLISHING_FAIL);
    }
}
