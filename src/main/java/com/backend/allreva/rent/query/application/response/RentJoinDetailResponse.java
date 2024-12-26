package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDateTime;

public record RentJoinDetailResponse(
        Long rentJoinId, // 차량 대절 참여 ID
        LocalDateTime applyDate, // 신청 날짜
        String depositorName, // 입금자명
        String phone, // 연락처
        int passengerNum, // 탑승 인원
        BoardingType boardingType, // 이용 편도
        String depositorTime, // 입금 시각
        RefundType refundType, // 환불 정책
        String rentAccount // 환불 계좌
) {

}
