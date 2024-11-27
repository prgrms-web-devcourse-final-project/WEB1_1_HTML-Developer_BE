package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.SurveyQueryRepository;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.backend.allreva.survey.command.domain.QSurvey.survey;
import static com.backend.allreva.survey.command.domain.QSurveyJoin.surveyJoin;

@Repository
@RequiredArgsConstructor
public class SurveyQueryRepositoryImpl implements SurveyQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public SurveyDetailResponse findSurveyDetail(final Long surveyId) {
        return queryFactory
                .from(survey)
                .where(survey.id.eq(surveyId))
                .transform(
                        GroupBy.groupBy(survey.id).as(
                                surveyDetailProjections()
                        )
                ).get(surveyId);
    }

    private static ConstructorExpression<SurveyDetailResponse> surveyDetailProjections() {
        return Projections.constructor(SurveyDetailResponse.class,
                survey.id,
                survey.title,
                GroupBy.list(survey.boardingDate), // 그룹화하여 List로 반환
                survey.information,
                survey.isClosed
        );
    }

    @Override
    public List<SurveySummaryResponse> findSurveyList(final Region region,
                                                      final SortType sortType,
                                                      final Long lastId,
                                                      final LocalDate lastEndDate,
                                                      final int pageSize) {

        return queryFactory
                .select(surveySummaryProjections())
                .from(survey)
                .where(survey.endDate.goe(LocalDate.now()),
                        getRegionCondition(region),
                        getPagingCondition(sortType, lastId, lastEndDate))
                .orderBy(orderSpecifiers(sortType))
                .limit(pageSize)
                .fetch();
    }

    private ConstructorExpression<SurveySummaryResponse> surveySummaryProjections() {
        return Projections.constructor(SurveySummaryResponse.class,
                survey.id,
                survey.title,
                survey.region,
                ExpressionUtils.as(
                        getParticipantCount(survey.id), "participantCount"
                ),
                survey.endDate
        );
    }

    private JPQLQuery<Long> getParticipantCount(final NumberPath<Long> surveyId) {
        return JPAExpressions.select(surveyJoin.count())
                .from(surveyJoin)
                .where(surveyJoin.surveyId.eq(surveyId));
    }

    private static BooleanExpression getRegionCondition(final Region region) {
        return region == null ? null : survey.region.eq(region);
    }

    private BooleanExpression getPagingCondition(final SortType sortType,
                                                 final Long lastId,
                                                 final LocalDate lastEndDate) {
        //첫페이지인 경우 조건 없음
        if (lastId == null && lastEndDate == null) {
            return null;
        }

        switch (sortType) {
            case CLOSING -> {
                return (survey.endDate.lt(lastEndDate))
                        .or(survey.endDate.eq(lastEndDate).and(survey.id.gt(lastId))); //endDate가 같을 경우 lastId 오래된 순
            }
            case OLDEST -> {
                return survey.id.gt(lastId);
            }
            default -> {
                return survey.id.lt(lastId);
            }
        }
    }

    private OrderSpecifier<?>[] orderSpecifiers(final SortType sortType) {
        switch (sortType) {
            case CLOSING -> {
                return new OrderSpecifier[]{
                        survey.endDate.asc(),
                        survey.id.asc()
                };
            }
            case OLDEST -> {
                return new OrderSpecifier[]{
                        survey.id.asc()
                };
            }
            default -> {
                return new OrderSpecifier[]{
                        survey.id.desc()
                };
            }
        }
    }
}
