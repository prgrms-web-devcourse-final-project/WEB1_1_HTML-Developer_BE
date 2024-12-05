package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;

public record RentAdminJoinDetailResponse(
        Long rentJoinId,
        String depositorName,
        String phone,
        int passengerNum,
        BoardingType boardingType,
        String depositorTime,
        RefundType refundType,
        String rentAccount
) {

}
