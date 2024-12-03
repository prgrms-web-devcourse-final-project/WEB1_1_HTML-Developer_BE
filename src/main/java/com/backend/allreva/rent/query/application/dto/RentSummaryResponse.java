package com.backend.allreva.rent.query.application.dto;

import java.time.LocalDate;

public record RentSummaryResponse(
        // 메인 페이지, 마이 페이지 겹치는 데이터
        String title,
        String boardingArea,
        LocalDate endDate,
        int maxRecruitmentCount, // 최대 모집 인원
        int currentRecruitmentCount, // 현재 모집 인원
        LocalDate rentBoardingDate, // 공연일(차 대절 가용 날짜)
        boolean isClosed,
        // 메인 페이지
        String imageUrl,
        // 마이 페이지
        LocalDate createdAt
) {

}
