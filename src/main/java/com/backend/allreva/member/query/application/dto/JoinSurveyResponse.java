package com.backend.allreva.member.query.application.dto;

import com.backend.allreva.survey.command.domain.value.BoardingType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public final class JoinSurveyResponse {
    private final SurveyResponse surveyResponse;
    private final Long surveyJoinId;
    private final LocalDate participationDate;
    private final BoardingType boardingType;
    private final Integer passengerNum;

    public JoinSurveyResponse(final SurveyResponse surveyResponse,
                              final Long surveyJoinId,
                              final LocalDateTime participationDate,
                              final BoardingType boardingType,
                              final Integer passengerNum) {
        this.surveyResponse = surveyResponse;
        this.surveyJoinId = surveyJoinId;
        this.participationDate = participationDate.toLocalDate();
        this.boardingType = boardingType;
        this.passengerNum = passengerNum;
    }
}
