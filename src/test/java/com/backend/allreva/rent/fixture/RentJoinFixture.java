package com.backend.allreva.rent.fixture;

import com.backend.allreva.rent_join.command.domain.RentJoin;
import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.Depositor;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentJoinFixture {

    public static RentJoin createRentJoinFixture(
            final Long rentId,
            final Long memberId
    ) {
        return RentJoin.builder()
                .rentId(rentId)
                .memberId(memberId)
                .depositor(Depositor.builder()
                        .depositorName("홍길동")
                        .depositorTime("21:30")
                        .phone("010-1234-5678")
                        .build())
                .passengerNum(1)
                .boardingType(BoardingType.ROUND)
                .refundType(RefundType.BOTH)
                .refundAccount("123-4567-4344-23")
                .boardingDate(LocalDate.of(2024, 9, 20))
                .build();
    }
}
