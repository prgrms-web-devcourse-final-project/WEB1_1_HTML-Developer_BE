package com.backend.allreva.rentJoin.command.application.request;

import com.backend.allreva.rentJoin.command.domain.value.BoardingType;
import com.backend.allreva.rentJoin.command.domain.value.RefundType;
import java.time.LocalDate;

public record RentJoinUpdateRequest(
        Long rentJoinId,
        String depositorName,
        String depositorTime,
        String phone,
        int passengerNum,
        BoardingType boardingType,
        RefundType refundType,
        String refundAccount,
        LocalDate boardingDate
) {

}
