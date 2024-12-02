package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.command.domain.value.BoardingType;
import com.backend.allreva.survey.query.application.MemberSurveyRepository;
import com.backend.allreva.survey.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.survey.query.application.dto.JoinSurveyResponse;
import com.backend.allreva.survey.query.application.dto.SurveyResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
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
public class MemberSurveyRepositoryImpl implements MemberSurveyRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId,
                                                            final Long lastId,
                                                            final LocalDate lastBoardingDate,
                                                            final int pageSize) {

        return jpaQueryFactory
                .select(CreatedSurveyProjections())
                .from(survey)
                .join(surveyBoardingDate)
                .on(survey.id.eq(surveyBoardingDate.survey.id))
                .leftJoin(surveyJoin)
                .on(surveyBoardingDate.date.eq(surveyJoin.boardingDate)
                        .and(survey.id.eq(surveyJoin.surveyId)))
                .where(survey.memberId.eq(memberId),
                        getPagingCondition(lastId, lastBoardingDate))
                .groupBy(survey.id, surveyBoardingDate.date)
                .orderBy(survey.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private ConstructorExpression<CreatedSurveyResponse> CreatedSurveyProjections() {

        return Projections.constructor(CreatedSurveyResponse.class,
                Projections.constructor(SurveyResponse.class,
                        survey.id,
                        survey.title,
                        surveyBoardingDate.date,
                        survey.region,
                        survey.createdAt,
                        survey.endDate,
                        surveyJoin.passengerNum.sum().coalesce(0),
                        survey.maxPassenger),
                getPassengerSumByBoardingType(BoardingType.UP),
                getPassengerSumByBoardingType(BoardingType.DOWN),
                getPassengerSumByBoardingType(BoardingType.ROUND));
    }


    private BooleanExpression getPagingCondition(final Long lastId, final LocalDate lastBoardingDate) {
        //첫페이지인 경우 조건 없음
        if (lastId == null && lastBoardingDate == null) {
            return null;
        }
        return survey.id.lt(lastId)
                .or(survey.id.eq(lastId).and(surveyBoardingDate.date.gt(lastBoardingDate)));
    }

    //해당하는 boardingType이면 passengerNum을 더함
    private NumberExpression<Integer> getPassengerSumByBoardingType(final BoardingType boardingType) {
        return Expressions.cases()
                .when(surveyJoin.boardingType.eq(boardingType))
                .then(surveyJoin.passengerNum)
                .otherwise(0)
                .sum();
    }


    @Override
    public List<JoinSurveyResponse> getJoinSurveyList(final Long memberId,
                                                      final Long lastId,
                                                      final int pageSize) {
        return jpaQueryFactory
                .select(JoinSurveyProjections())
                .from(survey)
                .join(surveyBoardingDate)
                .on(survey.id.eq(surveyBoardingDate.survey.id))
                .join(surveyJoin)
                .on(surveyBoardingDate.date.eq(surveyJoin.boardingDate)
                        .and(survey.id.eq(surveyJoin.surveyId)))
                .where(surveyJoin.memberId.eq(memberId),
                        getPagingCondition(lastId))
                .orderBy(surveyJoin.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private ConstructorExpression<JoinSurveyResponse> JoinSurveyProjections() {

        return Projections.constructor(JoinSurveyResponse.class,
                Projections.constructor(SurveyResponse.class,
                        survey.id,
                        survey.title,
                        surveyBoardingDate.date,
                        survey.region,
                        survey.createdAt,
                        survey.endDate,
                        getParticipationCount(),
                        survey.maxPassenger),
                surveyJoin.id,
                surveyJoin.createdAt,
                surveyJoin.boardingType,
                surveyJoin.passengerNum
        );
    }

    private static Expression<Integer> getParticipationCount() {
        return ExpressionUtils.as(JPAExpressions
                .select(surveyJoin.passengerNum.sum())
                .from(surveyJoin)
                .where(surveyJoin.surveyId.eq(survey.id)
                        .and(surveyJoin.boardingDate.eq(surveyBoardingDate.date)))
                .groupBy(surveyJoin.surveyId, surveyJoin.boardingDate), "participationCount");
    }

    private BooleanExpression getPagingCondition(final Long lastId) {
        if (lastId == null) {
            return null;
        }

        return survey.id.lt(lastId);
    }
}
