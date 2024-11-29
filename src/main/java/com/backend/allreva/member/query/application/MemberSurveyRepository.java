package com.backend.allreva.member.query.application;

import com.backend.allreva.member.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.member.query.application.dto.JoinSurveyResponse;

import java.util.List;

public interface MemberSurveyRepository {
    List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId, final Long lastId, final int pageSize);

    List<JoinSurveyResponse> getJoinSurveyList(final Long memberId, final Long lastId, final int pageSize);

}
