package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.exception.ConcertNotFoundException;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.backend.allreva.common.model.QImage.image;
import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.concert.command.domain.value.QSeller.seller;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;


@RequiredArgsConstructor
@Repository
public class ConcertDslRepositoryImpl implements ConcertDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ConcertDetailResponse findDetailById(final Long concertId) {
        ConcertDetailResponse response = queryFactory
                .from(concert)
                .leftJoin(concert.detailImages, image)
                .leftJoin(concert.sellers, seller)
                .join(concertHall).on(concertHall.id.eq(concert.code.hallCode))
                .where(concert.id.eq(concertId))
                .transform(GroupBy.groupBy(concert.id)
                        .as(concertDetailProjection()))
                .get(concertId);

        if (response == null) {
            return ConcertDetailResponse.EMPTY;
        }
        return response;
    }

    private ConstructorExpression<ConcertDetailResponse> concertDetailProjection() {
        return Projections.constructor(ConcertDetailResponse.class,
                concert.poster,
                GroupBy.set(image),
                concert.concertInfo,
                GroupBy.set(seller),
                concertHall.id,
                concertHall.name,
                concertHall.seatScale,
                concertHall.convenienceInfo,
                concertHall.location.address
        );
    }
}

