package com.backend.allreva.member.query.application.dto;

import com.backend.allreva.survey.command.domain.value.BoardingType;
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
                              final Integer passengerNum) {
        this.surveyResponse = surveyResponse;
        this.surveyJoinId = surveyJoinId;
        this.applyDate = applyDate.toLocalDate();
        this.boardingType = boardingType;
        this.passengerNum = passengerNum;
    }
}
