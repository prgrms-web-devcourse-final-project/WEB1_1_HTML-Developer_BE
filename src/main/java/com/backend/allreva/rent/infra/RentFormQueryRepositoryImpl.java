package com.backend.allreva.rent.infra;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;
import static com.backend.allreva.rent.command.domain.QRentForm.rentForm;
import static com.backend.allreva.rent.command.domain.QRentFormBoardingDate.rentFormBoardingDate;
import static com.querydsl.core.types.Projections.list;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.RentFormQueryRepository;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentFormSummaryResponse;
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
public class RentFormQueryRepositoryImpl implements RentFormQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RentFormDetailResponse> findRentFormDetailById(final Long rentFormId) {
        RentFormDetailResponse rentFormDetailResponse = queryFactory
                .select(rentFormDetailProjections())
                .from(rentForm)
                .where(rentForm.id.eq(rentFormId))
                .leftJoin(concert).on(rentForm.concertId.eq(concert.id))
                .leftJoin(concertHall).on(concert.code.hallCode.eq(concertHall.id))
                .join(rentFormBoardingDate).on(rentForm.id.eq(rentFormBoardingDate.rentForm.id))
                .fetchOne();
        return Optional.ofNullable(rentFormDetailResponse);
    }

    public ConstructorExpression<RentFormDetailResponse> rentFormDetailProjections() {
        return Projections.constructor(RentFormDetailResponse.class,
                concert.concertInfo.title,
                rentForm.detailInfo.image.url,
                rentForm.detailInfo.title,
                rentForm.detailInfo.artistName,
                rentForm.detailInfo.region,
                rentForm.operationInfo.boardingArea, // 상행 지역
                concertHall.name, // 하행 지역
                rentForm.operationInfo.upTime,
                rentForm.operationInfo.downTime,
                list(rentFormBoardingDate.date),
                rentForm.operationInfo.bus.busSize,
                rentForm.operationInfo.bus.busType,
                rentForm.operationInfo.bus.maxPassenger,
                rentForm.operationInfo.price.roundPrice,
                rentForm.operationInfo.price.upTimePrice,
                rentForm.operationInfo.price.downTimePrice,
                rentForm.additionalInfo.recruitmentCount,
                rentForm.additionalInfo.endDate,
                rentForm.additionalInfo.chatUrl,
                rentForm.additionalInfo.refundType,
                rentForm.additionalInfo.information
        );
    }

    @Override
    public Optional<DepositAccountResponse> findDepositAccountById(final Long rentFormId) {
        DepositAccountResponse depositAccountResponse = queryFactory
                .select(Projections.constructor(DepositAccountResponse.class,
                        rentForm.detailInfo.depositAccount))
                .from(rentForm)
                .where(rentForm.id.eq(rentFormId))
                .fetchOne();
        return Optional.ofNullable(depositAccountResponse);
    }

    @Override
    public List<RentFormSummaryResponse> findRentFormSummaries(
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
