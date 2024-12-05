package com.backend.allreva.rent.command.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;
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
