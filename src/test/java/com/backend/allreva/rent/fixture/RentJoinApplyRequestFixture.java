package com.backend.allreva.rent.fixture;

import com.backend.allreva.rentJoin.command.application.request.RentJoinApplyRequest;
import com.backend.allreva.rentJoin.command.domain.value.BoardingType;
import com.backend.allreva.rentJoin.command.domain.value.RefundType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentJoinApplyRequestFixture {

    public static RentJoinApplyRequest createRentJoinApplyRequestFixture(final Long rentId) {
        return new RentJoinApplyRequest(
                rentId,
                "홍길동",
                "21:30",
                "010-1234-5678",
                3,
                BoardingType.ROUND,
                RefundType.BOTH,
                "123-4567-4344-23",
                LocalDate.of(2024, 9, 20)
        );
    }
}
