package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;

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
