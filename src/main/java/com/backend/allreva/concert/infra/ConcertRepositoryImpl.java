package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.query.application.ConcertRepositoryCustom;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.backend.allreva.concert.query.domain.QConcert.concert;
import static com.backend.allreva.hall.query.domain.QConcertHall.concertHall;


@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ConcertDetail findDetailById(Long concertId) {
        return queryFactory
                .select(concertDetailProjection())
                .from(concert)
                .join(concertHall).on(concertHall.id.eq(concert.hallId))
                .where(concert.id.eq(concertId))
                .fetchFirst();
    }

    private QBean<ConcertDetail> concertDetailProjection() {
        return Projections.fields(ConcertDetail.class,
                concert.poster.as("poster"),
                concert.detailImages.as("detailImages"),
                concert.concertInfo.as("concertInfo"),
                concert.sellers.as("sellers"),
                concert.prfstate.as("concertStatus"),
                concertHall.location.as("location")
                );
    }

/*    private QBean<ConcertDetail> concertDetailProjection1() {
        return Projections.fields(ConcertDetail.class,
                concert.poster.as("poster"),
                concert.detailImages.as("detailImages"),
                concert.sellers.as("sellers"),
                concert.concertInfo.as("concertInfo"),
                concert.prfstate.as("concertStatus"),
                concertHall.location.as("location")
        );
    }*/
}
