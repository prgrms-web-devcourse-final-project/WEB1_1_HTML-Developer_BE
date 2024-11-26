package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.SurveyQueryRepository;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.backend.allreva.survey.command.domain.QSurvey.survey;
import static com.backend.allreva.survey.command.domain.QSurveyJoin.surveyJoin;

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

    @Override
    public List<SurveySummaryResponse> findSurveyList(Region region, SortType sortType, Long lastId, LocalDate lastEndDate, int pageSize) {
        BooleanExpression regionCondition = region == null ? null : survey.region.eq(region);

        return queryFactory
                .select(Projections.constructor(SurveySummaryResponse.class,
                        survey.id,
                        survey.title,
                        survey.region,
                        ExpressionUtils.as(
                                getParticipantCount(survey.id)
                                , "participantCount"),
                        survey.endDate
                ))
                .from(survey)
                .where(survey.endDate.goe(LocalDate.now()), regionCondition)
                .orderBy(orderSpecifier(sortType)).fetch();
    }

    private OrderSpecifier<LocalDateTime> orderSpecifier(SortType sortType) {
        switch (sortType) {
            case OLDEST -> {
                return survey.createdAt.asc();
            }
            case CLOSING -> {
                return Expressions.dateTemplate(LocalDateTime.class, "{0}T00:00:00", survey.endDate).asc();
            }
            default -> {
                return survey.createdAt.desc();
            }
        }
    }

    private JPQLQuery<Long> getParticipantCount(NumberPath<Long> surveyId) {
        return JPAExpressions.select(surveyJoin.count())
                .from(surveyJoin)
                .where(surveyJoin.surveyId.eq(surveyId));
    }
}
