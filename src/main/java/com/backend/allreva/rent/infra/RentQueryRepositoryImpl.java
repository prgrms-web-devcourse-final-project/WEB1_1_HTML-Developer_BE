package com.backend.allreva.rent.infra;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;
import static com.backend.allreva.rent.command.domain.QRent.rent;
import static com.backend.allreva.rent.command.domain.QRentBoardingDate.rentBoardingDate;
import static com.backend.allreva.rent.command.domain.QRentJoin.rentJoin;
import static com.backend.allreva.survey.command.domain.QSurvey.survey;
import static com.querydsl.core.types.Projections.list;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentQueryRepository;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinCountDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinSummaryResponse;
import com.backend.allreva.rent.query.application.dto.RentSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentQueryRepositoryImpl implements RentQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 차 대절 메인 페이지 조회
     */
    @Override
    public List<RentSummaryResponse> findRentSummaries(
            final Region region,
            final SortType sortType,
            final LocalDate lastEndDate,
            final Long lastId,
            final int pageSize
    ) {
        return queryFactory
                .select(Projections.constructor(RentSummaryResponse.class,
                        concert.concertInfo.title,
                        rent.operationInfo.boardingArea,
                        rent.additionalInfo.endDate,
                        rent.additionalInfo.recruitmentCount,// count 어떻게 해야 할지
                        rent.additionalInfo.recruitmentCount,
                        rentBoardingDate.date,
                        rent.isClosed,
                        rent.detailInfo.image.url,
                        rent.createdAt
                ))
                .from(rent)
                .leftJoin(concert).on(rent.concertId.eq(concert.id))
                .leftJoin(concertHall).on(concert.code.hallCode.eq(concertHall.id))
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id))
                .where(
                        rent.detailInfo.region.eq(region),
                        getPagingCondition(sortType, lastId, lastEndDate)
                )
                .groupBy(rent.id)
                .orderBy(orderSpecifiers(sortType))
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression getPagingCondition(
            final SortType sortType,
            final Long lastId,
            final LocalDate lastEndDate
    ) {
        if (lastId == null && lastEndDate == null) {
            return null;
        }

        switch (sortType) {
            case CLOSING -> {
                return (rent.additionalInfo.endDate.gt(lastEndDate))
                        .or(rent.additionalInfo.endDate.eq(lastEndDate).and(rent.id.gt(lastId)));
            }
            case OLDEST -> {
                return rent.id.gt(lastId);
            }
            case LATEST -> {
                return rent.id.lt(lastId);
            }
            default -> {
                return null;
            }
        }
    }

    private OrderSpecifier<?>[] orderSpecifiers(final SortType sortType) {
        switch (sortType) {
            case CLOSING -> {
                return new OrderSpecifier[]{
                        rent.additionalInfo.endDate.asc(),
                        rent.detailInfo.title.asc()
                };
            }
            case OLDEST -> {
                return new OrderSpecifier[]{
                        rent.createdAt.asc(),
                        rent.detailInfo.title.asc()
                };
            }
            case LATEST -> {
                return new OrderSpecifier[]{
                        rent.createdAt.desc(),
                        rent.detailInfo.title.asc()
                };
            }
            default -> {
                return new OrderSpecifier[0];
            }
        }
    }

    /**
     * 자신이 참여한 차 대절 조회
     */
    @Override
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
                        rentJoin.depositor.depositorTime,
                        rentJoin.depositor.phone,
                        rentJoin.passengerNum,
                        rentJoin.boardingType,
                        rentJoin.depositor.depositorTime,
                        rentJoin.refundType
                ))
                .from(rent)
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id))
                .from(rentJoin)
                .join(rent).on(rentJoin.rentId.eq(rent.id)
                        .and(rentJoin.boardingDate.eq(rentBoardingDate.date)))
                .where(rentJoin.memberId.eq(memberId))
                .groupBy(rent.id, rentBoardingDate.date)
                .fetch();
    }

    /**
     * 차 대절 현재 참여자 수 서브 쿼리
     */
    private Expression<Integer> getParticipationCount() {
        return ExpressionUtils.as(JPAExpressions
                .select(rentJoin.passengerNum.sum())
                .from(rentJoin)
                .where(rentJoin.rentId.eq(survey.id)
                        .and(rentJoin.boardingDate.eq(rentBoardingDate.date)))
                .groupBy(rentJoin.rentId, rentJoin.boardingDate), "participationCount");
    }

    /**
     * 차 대절 상세 조회
     */
    @Override
    public Optional<RentDetailResponse> findRentDetailById(final Long rentId) {
        RentDetailResponse rentDetailResponse = queryFactory
                .select(rentDetailProjections())
                .from(rent)
                .where(rent.id.eq(rentId))
                .leftJoin(concert).on(rent.concertId.eq(concert.id))
                .leftJoin(concertHall).on(concert.code.hallCode.eq(concertHall.id))
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id))
                .fetchOne();
        return Optional.ofNullable(rentDetailResponse);
    }

    public ConstructorExpression<RentDetailResponse> rentDetailProjections() {
        return Projections.constructor(RentDetailResponse.class,
                concert.concertInfo.title,
                rent.detailInfo.image.url,
                rent.detailInfo.title,
                rent.detailInfo.artistName,
                rent.detailInfo.region,
                rent.operationInfo.boardingArea, // 상행 지역
                concertHall.name, // 하행 지역
                rent.operationInfo.upTime,
                rent.operationInfo.downTime,
                list(rentBoardingDate.date),
                rent.operationInfo.bus.busSize,
                rent.operationInfo.bus.busType,
                rent.operationInfo.bus.maxPassenger,
                rent.operationInfo.price.roundPrice,
                rent.operationInfo.price.upTimePrice,
                rent.operationInfo.price.downTimePrice,
                rent.additionalInfo.recruitmentCount,
                rent.additionalInfo.endDate,
                rent.additionalInfo.chatUrl,
                rent.additionalInfo.refundType,
                rent.additionalInfo.information
        );
    }

    /**
     * 입금 계좌 조회
     */
    @Override
    public Optional<DepositAccountResponse> findDepositAccountById(final Long rentId) {
        DepositAccountResponse depositAccountResponse = queryFactory
                .select(Projections.constructor(DepositAccountResponse.class,
                        rent.detailInfo.depositAccount))
                .from(rent)
                .where(rent.id.eq(rentId))
                .fetchOne();
        return Optional.ofNullable(depositAccountResponse);
    }

    /**
     * 이용 구분, 입금 처리 수 조회
     */
    @Override
    public Optional<RentJoinCountDetailResponse> findRentJoinCountDetailById(final Long rentId) {
        RentJoinCountDetailResponse rentCountResponse = queryFactory
                .select(Projections.constructor(RentJoinCountDetailResponse.class,
                        getRentBoardingCount(BoardingType.UP, "RentUpCount"),
                        getRentBoardingCount(BoardingType.DOWN, "RentDownCount"),
                        getRentBoardingCount(BoardingType.ROUND, "RentRoundCount"),
                        getRefundCount(RefundType.REFUND, "refundCount"),
                        getRefundCount(RefundType.ADDITIONAL_DEPOSIT, "additionalDepositCount")))
                .from(rent)
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id))
                .join(rentJoin).on(rentJoin.rentId.eq(rent.id)
                        .and(rentJoin.boardingDate.eq(rentBoardingDate.date)))
                .where(rent.id.eq(rentId))
                .fetchOne();

        return Optional.ofNullable(rentCountResponse);
    }

    private NumberExpression<Integer> getRentBoardingCount(final BoardingType boardingType, final String alias) {
        return rentJoin.boardingType
                .when(boardingType)
                .then(rentJoin.passengerNum.sum().intValue())
                .otherwise(0)
                .as(alias);
    }

    private NumberExpression<Integer> getRefundCount(final RefundType refundType, final String alias) {
        return rentJoin.refundType
                .when(refundType)
                .then(rentJoin.passengerNum.sum().intValue())
                .otherwise(0)
                .add(rentJoin.refundType
                        .when(RefundType.BOTH)
                        .then(rentJoin.passengerNum.sum().intValue())
                        .otherwise(0))
                .as(alias);
    }

    /**
     * 차 대절 참여자 상세 조회
     */
    @Override
    public List<RentJoinDetailResponse> findRentJoinDetailsById(final Long rentId) {
        return queryFactory.select(Projections.constructor(RentJoinDetailResponse.class,
                        rentJoin.id,
                        rentJoin.depositor.depositorName,
                        rentJoin.depositor.phone,
                        rentJoin.passengerNum,
                        rentJoin.boardingType,
                        rentJoin.depositor.depositorTime,
                        rentJoin.refundType
                ))
                .from(rentJoin)
                .where(rentJoin.rentId.eq(rentId))
                .fetch();
    }
}
