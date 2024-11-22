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
    public List<ConcertHallDetail> findDetailByFacilityCode(String facilityCode) {
        return queryFactory.select(hallDetailProjections())
                .from(concertHall)
                .where(concertHall.facilityInfo.fcltyCode.eq(facilityCode))
                .fetch();
    }

    private static ConstructorExpression<ConcertHallDetail> hallDetailProjections() {
        return Projections.constructor(ConcertHallDetail.class,
                concertHall.facilityInfo,
                concertHall.hallInfo
        );
    }


}
