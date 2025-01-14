package com.backend.allreva.concert.infra.rdb;

import com.backend.allreva.common.exception.NotFoundException;
import com.backend.allreva.concert.query.application.response.ConcertDetailResponse;
import com.backend.allreva.concert.query.application.response.ConcertThumbnail;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<ConcertThumbnail> getConcertMainThumbnails() {
        return queryFactory
                .select(Projections.constructor(ConcertThumbnail.class,
                        concert.poster.url,
                        concert.concertInfo.title,
                        concertHall.name,
                        concert.concertInfo.dateInfo.startDate,
                        concert.concertInfo.dateInfo.endDate,
                        concert.id
                ))
                .from(concert)
                .leftJoin(concertHall).on(concert.code.hallCode.eq(concertHall.id))
                .where(concert.concertInfo.dateInfo.endDate.goe(LocalDate.now()))
                .orderBy(concert.concertInfo.dateInfo.startDate.asc())
                .limit(5)
                .fetch();
    }

    private ConstructorExpression<ConcertDetailResponse> concertDetailProjection() {
        return Projections.constructor(ConcertDetailResponse.class,
                concert.poster,
                GroupBy.list(image),
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

