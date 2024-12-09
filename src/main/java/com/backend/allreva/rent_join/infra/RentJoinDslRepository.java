package com.backend.allreva.rent_join.infra;

import com.backend.allreva.rent_join.query.response.RentJoinSummaryResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.allreva.rent.command.domain.QRent.rent;
import static com.backend.allreva.rent.command.domain.QRentBoardingDate.rentBoardingDate;
import static com.backend.allreva.rent_join.command.domain.QRentJoin.rentJoin;

@RequiredArgsConstructor
@Repository
public class RentJoinDslRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * [Participate] 자신이 참여한 차 대절 조회
     */
    public List<RentJoinSummaryResponse> findRentJoinSummariesByMemberId(final Long memberId) {
        return queryFactory.select(Projections.constructor(RentJoinSummaryResponse.class,
                        rent.detailInfo.title,
                        rent.operationInfo.boardingArea,
                        rent.additionalInfo.endDate,
                        rent.additionalInfo.recruitmentCount,
                        getParticipationCount(), // 현재 참여자 수
                        rentBoardingDate.date,
                        rent.isClosed,
                        rent.createdAt,
                        rentJoin.id,
                        rentJoin.depositor.depositorName,
                        rentJoin.depositor.phone,
                        rentJoin.passengerNum,
                        rentJoin.boardingType,
                        rentJoin.depositor.depositorTime,
                        rentJoin.refundType
                ))
                .from(rentJoin)
                .join(rent).on(rentJoin.rentId.eq(rent.id))
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id)
                        .and(rentJoin.boardingDate.eq(rentBoardingDate.date)))
                .where(rentJoin.memberId.eq(memberId))
                .fetch();
    }

    /**
     * [SubQuery] 차 대절 현재 참여자 수
     */
    private Expression<Integer> getParticipationCount() {
        // 해당 쿼리를 rentBoardingDate 별로 나눠서 count를 구하고 싶어
        return ExpressionUtils.as(JPAExpressions
                .select(rentJoin.passengerNum.sum())
                .from(rentJoin)
                .where(rentJoin.rentId.eq(rent.id)
                        .and(rentJoin.boardingDate.eq(rentBoardingDate.date)))
                .groupBy(rentJoin.rentId, rentJoin.boardingDate), "participationCount");
    }
}
