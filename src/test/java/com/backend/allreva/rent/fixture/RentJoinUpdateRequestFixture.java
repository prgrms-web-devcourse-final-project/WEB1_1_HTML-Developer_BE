package com.backend.allreva.rent.fixture;

import com.backend.allreva.rent_join.command.application.request.RentJoinUpdateRequest;
import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentJoinUpdateRequestFixture {

    public static RentJoinUpdateRequest createRentJoinUpdateRequestFixture(
            final Long rentJoinId
    ) {
        return new RentJoinUpdateRequest(
                rentJoinId,
                LocalDate.of(2024, 9, 20),
                BoardingType.ROUND,
                3,
                "홍길동",
                "22:00", // fix
                "010-1234-5678",
                RefundType.BOTH,
                "123-4567-4344-23"
        );
    }
}
