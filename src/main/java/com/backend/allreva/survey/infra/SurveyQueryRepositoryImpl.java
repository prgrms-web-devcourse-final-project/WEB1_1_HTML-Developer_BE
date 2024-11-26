package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.query.application.SurveyQueryRepository;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.backend.allreva.survey.command.domain.QSurvey.survey;

@Repository
@RequiredArgsConstructor
public class SurveyQueryRepositoryImpl implements SurveyQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public SurveyDetailResponse findSurveyDetail(Long surveyId) {
        return queryFactory
                .from(survey)
                .where(survey.id.eq(surveyId))
                .transform(
                        GroupBy.groupBy(survey.id).as(
                                Projections.constructor(SurveyDetailResponse.class,
                                        survey.id,
                                        survey.title,
                                        GroupBy.list(survey.boardingDate), // 그룹화하여 List로 반환
                                        survey.information
                                )
                        )
                ).get(surveyId);
    }
}
