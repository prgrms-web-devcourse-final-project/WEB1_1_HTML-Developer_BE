package com.backend.allreva.member.infra;

import com.backend.allreva.member.query.application.MemberSurveyRepository;
import com.backend.allreva.member.query.application.dto.CreatedSurveyResponse;
import com.backend.allreva.member.query.application.dto.SurveyResponse;
import com.backend.allreva.survey.command.domain.value.BoardingType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.allreva.survey.command.domain.QSurvey.survey;
import static com.backend.allreva.survey.command.domain.QSurveyJoin.surveyJoin;

@Repository
@RequiredArgsConstructor
public class MemberSurveyRepositoryImpl implements MemberSurveyRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId,
                                                            final Long lastId,
                                                            final int pageSize) {

        return jpaQueryFactory.select(
                        Projections.constructor(CreatedSurveyResponse.class,
                                Projections.constructor(SurveyResponse.class,
                                        survey.id,
                                        survey.title,
                                        surveyJoin.boardingDate,
                                        survey.region,
                                        survey.createdAt,
                                        survey.eddate,
                                        surveyJoin.passengerNum.sum(),
                                        survey.maxPassenger),
                                getPassengerSumByBoardingType(BoardingType.UP),
                                getPassengerSumByBoardingType(BoardingType.DOWN),
                                getPassengerSumByBoardingType(BoardingType.ROUND))
                )
                .from(survey)
                .leftJoin(surveyJoin).on(survey.id.eq(surveyJoin.surveyId))
                .where(survey.memberId.eq(memberId),
                        getPagingCondition(lastId))
                .groupBy(survey.id, surveyJoin.boardingDate)
                .orderBy(survey.id.desc())
                .limit(pageSize)
                .fetch();


    }

/*    public List<CreatedSurveyResponse> getCreatedSurveyList22(final Long memberId,
                                                            final Long lastId,
                                                            final int pageSize) {

        return jpaQueryFactory.select(
                        Projections.constructor(CreatedSurveyResponse.class,
                                survey.id,
                                survey.title,
                                survey.boardingDate,
                                survey.region,
                                survey.createdAt,
                                survey.eddate,
                                surveyJoin.passengerNum.sum(),
                                survey.maxPassenger,
                                getPassengerSumByBoardingType(BoardingType.UP),
                                getPassengerSumByBoardingType(BoardingType.DOWN),
                                getPassengerSumByBoardingType(BoardingType.ROUND)))
                .leftJoin(surveyJoin).on(survey.id.eq(surveyJoin.surveyId))
                .where(survey.memberId.eq(memberId),
                        survey.boardingDate.contains(surveyJoin.boardingDate),
                        getPagingCondition(lastId))
                .groupBy(survey.id, surveyJoin.boardingDate)
                .orderBy(survey.id.desc())
                .limit(pageSize)
                .fetch();
    }*/

    private BooleanExpression getPagingCondition(final Long lastId) {
        if (lastId == null) {
            return null;
        }

        return survey.id.lt(lastId);
    }

    //해당하는 boardingType이면 passengerNum을 더함
    private NumberExpression<Integer> getPassengerSumByBoardingType(final BoardingType boardingType) {
        return Expressions.cases()
                .when(surveyJoin.boardingType.eq(boardingType))
                .then(surveyJoin.passengerNum)
                .otherwise(0)
                .sum();
    }

}
