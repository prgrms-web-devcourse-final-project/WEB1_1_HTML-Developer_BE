package com.backend.allreva.survey.exception;

import com.backend.allreva.common.exception.CustomException;

public class SurveyInvalidBoardingDateException extends CustomException {
    public SurveyInvalidBoardingDateException() {
        super(SurveyErrorCode.SURVEY_INVALID_BOARDING_DATE);
    }
}
