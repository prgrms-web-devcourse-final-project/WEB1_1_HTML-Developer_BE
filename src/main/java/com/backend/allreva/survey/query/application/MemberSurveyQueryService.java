package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.survey.query.application.dto.JoinSurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSurveyQueryService {
    private final MemberSurveyRepository memberSurveyRepository;

    public List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId, final Long lastId, final int pageSize) {
        return memberSurveyRepository.getCreatedSurveyList(memberId, lastId, pageSize);
    }

    public List<JoinSurveyResponse> getJoinSurveyList(final Long memberId, final Long lastId, final int pageSize) {
        return memberSurveyRepository.getJoinSurveyList(memberId, lastId, pageSize);
    }
}
