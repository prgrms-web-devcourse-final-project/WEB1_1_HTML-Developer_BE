package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;


public class SurveyNotWriterException extends CustomException {
    public SurveyNotWriterException() {
        super(SurveyErrorCode.SURVEY_NOT_WRITER);
    }
}
