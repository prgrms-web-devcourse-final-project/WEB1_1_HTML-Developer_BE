package com.backend.allreva.rent.infra;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;
import static com.backend.allreva.rent.command.domain.QRent.rent;
import static com.backend.allreva.rent.command.domain.QRentBoardingDate.rentBoardingDate;
import static com.querydsl.core.types.Projections.list;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentQueryRepository;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
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

    @Override
    public List<RentSummaryResponse> findRentSummaries(
            final Region region,
            final SortType sortType,
            final LocalDate lastEndDate,
            final Long lastId,
            final int pageSize
    ) {
        // TODO: 가용 날짜 구현 후 수정
        return List.of();
    }
}
