package com.backend.allreva.rent.query.application.dto;

/**
 * 참여자 신청 인원 수 상세 조회
 */
public record RentJoinCountDetailResponse(
        // BoardingType [상행, 하행, 왕복]
        int RentUpCount,
        int RentDownCount,
        int RentRoundCount,
        // RefundType [추가입금, 환불]
        int AdditionalDepositCount,
        int RefundCount
) {

}
