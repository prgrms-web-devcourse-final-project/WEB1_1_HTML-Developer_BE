package com.backend.allreva.survey.query.application.domain;

import com.backend.allreva.survey.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.survey.query.application.dto.JoinSurveyResponse;

import java.time.LocalDate;
import java.util.List;

public interface MemberSurveyRepository {
    List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId, final Long lastId, final LocalDate lastBoardingDate, final int pageSize);

    List<JoinSurveyResponse> getJoinSurveyList(final Long memberId, final Long lastId, final int pageSize);

}
