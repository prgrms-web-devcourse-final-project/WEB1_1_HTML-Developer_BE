package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.SurveyQueryRepository;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.backend.allreva.survey.command.domain.QSurvey.survey;
import static com.backend.allreva.survey.command.domain.QSurveyBoardingDate.surveyBoardingDate;
import static com.backend.allreva.survey.command.domain.QSurveyJoin.surveyJoin;

@Repository
@RequiredArgsConstructor
public class SurveyQueryRepositoryImpl implements SurveyQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public SurveyDetailResponse findSurveyDetail(final Long surveyId) {
        return queryFactory
                .from(survey)
                .join(surveyBoardingDate).on(survey.id.eq(surveyBoardingDate.survey.id))
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
                GroupBy.list(surveyBoardingDate.date),
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
                .leftJoin(surveyJoin).on(survey.id.eq(surveyJoin.surveyId))
                .where(survey.eddate.goe(LocalDate.now()),
                        getRegionCondition(region),
                        getPagingCondition(sortType, lastId, lastEndDate))
                .groupBy(survey.id, survey.title, survey.region, survey.eddate)
                .orderBy(orderSpecifiers(sortType))
                .limit(pageSize)
                .fetch();
    }

    private ConstructorExpression<SurveySummaryResponse> surveySummaryProjections() {
        return Projections.constructor(SurveySummaryResponse.class,
                survey.id,
                survey.title,
                survey.region,
                surveyJoin.passengerNum.sum().coalesce(0),
                survey.maxPassenger,
                survey.eddate
        );
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
                return (survey.eddate.gt(lastEndDate))
                        .or(survey.eddate.eq(lastEndDate).and(survey.id.gt(lastId))); //endDate가 같을 경우 lastId 오래된 순
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
                        survey.eddate.asc(),
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
