package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;

/**
 * 참여자 명단 상세 조회 [차량 대절에서 조회할 때]
 */
public record RentJoinDetailResponse(
        Long rentJoinId,
        String depositorName,
        String phone,
        String passengerNum,
        BoardingType boardingType,
        String depositorTime,
        RefundType refundType
) {

}
