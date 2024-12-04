package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import java.util.List;

public record RentAdminDetailResponse(
        Long rentId,
        int maxRecruitmentCount,
        int currentRecruitmentCount,
        // BoardingType [상행, 하행, 왕복]
        int rentUpCount,
        int rentDownCount,
        int rentRoundCount,
        // RefundType [추가입금, 환불]
        int additionalDepositCount,
        int refundCount,
        List<RentJoinDetailResponse> rentJoinDetailResponses
) {

    public record RentJoinDetailResponse(
            Long rentJoinId,
            String depositorName,
            String phone,
            int passengerNum,
            BoardingType boardingType,
            String depositorTime,
            RefundType refundType
    ) {

    }
}
