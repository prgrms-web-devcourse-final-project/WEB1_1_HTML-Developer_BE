package com.backend.allreva.rent.infra;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;
import static com.backend.allreva.rent.command.domain.QRentForm.rentForm;
import static com.querydsl.core.types.Projections.list;

import com.backend.allreva.rent.query.application.RentFormQueryRepository;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RentFormQueryRepositoryImpl implements RentFormQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RentFormDetailResponse> findById(final Long rentFormId) {
        RentFormDetailResponse rentFormDetailResponse = queryFactory
                .select(rentFormDetailProjections())
                .from(rentForm)
                .where(rentForm.id.eq(rentFormId))
                .leftJoin(concert).on(rentForm.concertId.eq(concert.id))
                .leftJoin(concertHall).on(concert.code.hallCode.eq(concertHall.id))
                .fetchOne();
        return Optional.of(rentFormDetailResponse);
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
                list(rentForm.operationInfo.boardingDates),
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
}
