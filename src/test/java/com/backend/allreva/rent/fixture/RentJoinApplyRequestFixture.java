package com.backend.allreva.rent.fixture;

import com.backend.allreva.rent_join.command.application.request.RentJoinApplyRequest;
import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentJoinApplyRequestFixture {

    public static RentJoinApplyRequest createRentJoinApplyRequestFixture(final Long rentId) {
        return new RentJoinApplyRequest(
                rentId,
                LocalDate.of(2024, 9, 20),
                BoardingType.ROUND,
                3,
                "홍길동",
                "21:30",
                "010-1234-5678",
                RefundType.BOTH,
                "123-4567-4344-23"
        );
    }
}
