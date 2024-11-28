package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;

public class SurveyNotFoundException extends CustomException {
    public SurveyNotFoundException() {
        super(SurveyErrorCode.SURVEY_NOT_FOUND);
    }
}
