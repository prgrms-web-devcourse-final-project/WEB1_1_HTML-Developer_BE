package com.backend.allreva.rent.query.application.dto;

import java.time.LocalDate;

/**
 * 참여자 신청 인원 수 조회
 */
public record RentJoinCountResponse(
        Long rentId,
        int maxRecruitmentCount, // 최대 모집 인원
        int currentRecruitmentCount, // 현재 모집 인원
        LocalDate rentBoardingDate // 공연일(차 대절 가용 날짜)
) {

}
