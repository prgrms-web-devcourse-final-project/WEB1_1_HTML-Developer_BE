package com.backend.allreva.rent.infra;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;
import static com.backend.allreva.rent.command.domain.QRent.rent;
import static com.backend.allreva.rent.command.domain.QRentBoardingDate.rentBoardingDate;
import static com.backend.allreva.rent.command.domain.QRentJoin.rentJoin;
import static com.querydsl.core.types.Projections.list;

import com.backend.allreva.common.util.DateHolder;
import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentQueryRepository;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminSummaryResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminJoinDetailResponse;
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
    private final DateHolder dateHolder;

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
                        rent.id,
                        rent.detailInfo.title,
                        rent.operationInfo.boardingArea,
                        rent.additionalInfo.endDate,
                        rent.detailInfo.image.url
                ))
                .from(rent)
                .where(
                        rent.additionalInfo.endDate.goe(dateHolder.getDate()),
                        rent.isClosed.eq(false),
                        getRegionCondition(region),
                        getPagingCondition(sortType, lastId, lastEndDate)
                )
                .groupBy(rent.id)
                .orderBy(orderSpecifiers(sortType))
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression getRegionCondition(final Region region) {
        return region == null ? null : rent.detailInfo.region.eq(region);
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
                .fetchFirst();
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
                list(getParticipationCount()),
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
                .fetchFirst();
        return Optional.ofNullable(depositAccountResponse);
    }

    /**
     * [Register] 등록한 차 대절 리스트 조회
     */
    public List<RentAdminSummaryResponse> findRentAdminSummariesByMemberId(final Long memberId) {
        return queryFactory.select(Projections.constructor(RentAdminSummaryResponse.class,
                        rent.id,
                        rent.detailInfo.title,
                        rentBoardingDate.date,
                        rent.operationInfo.boardingArea,
                        rent.operationInfo.bus.busSize,
                        rent.operationInfo.bus.busType,
                        rent.operationInfo.bus.maxPassenger,
                        rent.additionalInfo.endDate,
                        rent.createdAt,
                        rent.additionalInfo.recruitmentCount,
                        getParticipationCount(),
                        rent.isClosed
                ))
                .from(rent)
                .join(rentBoardingDate).on(rent.id.eq(rentBoardingDate.rent.id))
                .where(
                        rent.memberId.eq(memberId),
                        rent.isClosed.eq(false),
                        rentBoardingDate.date.goe(dateHolder.getDate()) // 마감 날짜 안지난것만
                )
                .fetch();
    }

    /**
     * [Register] 내가 등록한 차 대절 상세 조회
     * <p>
     * 이용 구분, 입금 처리에 대한 count를 반환합니다.
     */
    @Override
    public Optional<RentAdminDetailResponse> findRentAdminDetail(
            final Long memberId,
            final LocalDate boardingDate,
            final Long rentId
    ) {
        RentAdminDetailResponse rentAdminDetailResponse = queryFactory
                .select(Projections.constructor(RentAdminDetailResponse.class,
                        rent.id,
                        rent.additionalInfo.recruitmentCount,
                        ExpressionUtils.as(JPAExpressions
                                .select(rentJoin.passengerNum.sum())
                                .from(rentJoin)
                                .where(rentJoin.rentId.eq(rent.id)
                                        .and(rentJoin.boardingDate.eq(boardingDate)))
                                .groupBy(rentJoin.rentId, rentJoin.boardingDate), "participationCount"),
                        getRentBoardingCount(BoardingType.UP, "RentUpCount"),
                        getRentBoardingCount(BoardingType.DOWN, "RentDownCount"),
                        getRentBoardingCount(BoardingType.ROUND, "RentRoundCount"),
                        getRefundCount(RefundType.REFUND, "refundCount"),
                        getRefundCount(RefundType.ADDITIONAL_DEPOSIT, "additionalDepositCount")))
                .from(rent)
                .leftJoin(rentJoin).on(rentJoin.rentId.eq(rent.id))
                .where(
                        rent.memberId.eq(memberId),
                        rentJoin.boardingDate.eq(boardingDate),
                        rent.id.eq(rentId),
                        rent.isClosed.eq(false))
                .groupBy(rentJoin.boardingType, rentJoin.refundType)
                .fetchFirst();
        return Optional.ofNullable(rentAdminDetailResponse);
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
     * [Register] 차 대절 참가자 리스트 조회
     */
    @Override
    public List<RentAdminJoinDetailResponse> findRentAdminJoinDetails(
            final Long memberId,
            final Long rentId,
            final LocalDate boardingDate
    ) {
        return queryFactory.select(Projections.constructor(RentAdminJoinDetailResponse.class,
                        rentJoin.id,
                        rentJoin.depositor.depositorName,
                        rentJoin.depositor.phone,
                        rentJoin.passengerNum,
                        rentJoin.boardingType,
                        rentJoin.depositor.depositorTime,
                        rentJoin.refundType,
                        rentJoin.refundAccount
                ))
                .from(rent)
                .join(rentJoin).on(rentJoin.rentId.eq(rent.id))
                .where(
                        rent.memberId.eq(memberId),
                        rent.id.eq(rentId),
                        rentJoin.boardingDate.eq(boardingDate)
                )
                .fetch();
    }

    /**
     * [Participate] 자신이 참여한 차 대절 조회
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
}
