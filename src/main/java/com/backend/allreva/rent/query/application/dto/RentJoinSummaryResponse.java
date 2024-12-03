package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import java.time.LocalDate;

/**
 * 자신이 참여한 차량 대절 마이페이지
 */
public record RentJoinSummaryResponse(
        String title,
        String boardingArea,
        LocalDate endDate,
        int maxRecruitmentCount, // 최대 모집 인원
        int participateCount, // 현재 모집 인원
        LocalDate rentBoardingDate, // 공연일(차 대절 가용 날짜)
        boolean isClosed,
        LocalDate createdAt,
        Long rentJoinId,
        String depositorName,
        String phone,
        String passengerNum,
        BoardingType boardingType,
        String depositorTime,
        RefundType refundType
) {

}
