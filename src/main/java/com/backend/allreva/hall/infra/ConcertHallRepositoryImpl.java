package com.backend.allreva.hall.infra;

import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.allreva.hall.command.domain.QConcertHall.concertHall;

@RequiredArgsConstructor
@Repository
public class ConcertHallRepositoryImpl implements ConcertHallRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ConcertHallDetail findDetailByHallCode(String hallCode) {
        return queryFactory.select(hallDetailProjections())
                .from(concertHall)
                .where(concertHall.id.eq(hallCode))
                .fetchFirst();
    }

    private static ConstructorExpression<ConcertHallDetail> hallDetailProjections() {
        return Projections.constructor(ConcertHallDetail.class,
                concertHall.name,
                concertHall.seatScale,
                concertHall.star,
                concertHall.convenienceInfo,
                concertHall.location
        );
    }
}
