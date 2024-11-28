package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;

public class SurveyIllegalParameterException extends CustomException {

    public SurveyIllegalParameterException() {
        super(SurveyErrorCode.SURVEY_ILLEGAL_PARAMETER);
    }
}
