package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.query.application.ConcertRepositoryCustom;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.backend.allreva.concert.command.domain.QConcert.concert;
import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;


@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ConcertDetail findDetailById(Long concertId) {

        Tuple tuple = queryFactory.select(concert, concertHall.hallInfo, concertHall.convenienceInfo, concertHall.location)
                .from(concert)
                .join(concertHall).on(concertHall.id.eq(concert.code.hallCode))
                .where(concert.id.eq(concertId))
                .fetchFirst();

        if (tuple == null) {
            throw new IllegalArgumentException("No concert found with id " + concertId);
        }

        return ConcertDetail.builder()
                .concert(tuple.get(concert))
                .concertHallInfo(tuple.get(concertHall.hallInfo))
                .convenienceInfo(tuple.get(concertHall.convenienceInfo))
                .location(tuple.get(concertHall.location))
                .build();
    }

    private Expression<ConcertDetail> concertDetailProjection() {
        return Projections.constructor(ConcertDetail.class,
                concert,
                concertHall.hallInfo,
                concertHall.convenienceInfo,
                concertHall.location
        );
    }

}
