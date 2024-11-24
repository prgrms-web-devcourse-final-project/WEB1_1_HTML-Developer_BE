package com.backend.allreva.concert.infra;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.value.Seller;
import com.backend.allreva.concert.query.application.ConcertRepositoryCustom;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
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
public class ConcertRepositoryImpl implements ConcertRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ConcertDetail findDetailById(Long concertId) {
        return queryFactory.select(concertDetailProjection())
                .from(concert)
                .join(concertHall).on(concertHall.id.eq(concert.code.hallCode))
                .leftJoin(concert.detailImages, image)
                .leftJoin(concert.sellers, seller)
                .where(concert.id.eq(concertId))
                .transform(GroupBy.groupBy(concert.id)
                        .list(transformProjection()))
                .get(0);



    }

    private Expression<ConcertDetail> concertDetailProjection() {
        return Projections.constructor(ConcertDetail.class,
                concert.poster,
                GroupBy.list(concert.detailImages),
                concert.concertInfo,
                GroupBy.list(concert.sellers),

                concertHall.id,
                concertHall.name,
                concertHall.seatScale,
                concertHall.convenienceInfo,
                concertHall.location.address
        );
    }

    private Expression<ConcertDetail> transformProjection() {
        return Projections.constructor(ConcertDetail.class,
                concert.poster,
                Projections.list(Projections.constructor(Image.class, image.url)),
                concert.concertInfo,
                Projections.list(Projections.constructor(Seller.class, seller.relateName, seller.relateUrl)),
                concertHall.id,
                concertHall.name,
                concertHall.seatScale,
                concertHall.convenienceInfo,
                concertHall.location.address
        );



    }
}

