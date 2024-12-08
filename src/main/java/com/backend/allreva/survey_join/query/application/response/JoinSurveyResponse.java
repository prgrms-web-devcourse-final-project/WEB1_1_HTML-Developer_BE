package com.backend.allreva.survey_join.query.application.response;

import com.backend.allreva.survey.query.application.response.SurveyResponse;
import com.backend.allreva.survey_join.command.domain.value.BoardingType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public final class JoinSurveyResponse {
    private final SurveyResponse surveyResponse;
    private final Long surveyJoinId;
    private final LocalDate applyDate;
    private final BoardingType boardingType;
    private final Integer passengerNum;

    public JoinSurveyResponse(final SurveyResponse surveyResponse,
                              final Long surveyJoinId,
                              final LocalDateTime applyDate,
                              final BoardingType boardingType,
                              final int passengerNum) {
        this.surveyResponse = surveyResponse;
        this.surveyJoinId = surveyJoinId;
        this.applyDate = applyDate.toLocalDate();
        this.boardingType = boardingType;
        this.passengerNum = passengerNum;
    }
}
