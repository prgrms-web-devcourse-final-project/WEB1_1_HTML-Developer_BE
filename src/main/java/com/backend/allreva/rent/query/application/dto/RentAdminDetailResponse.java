package com.backend.allreva.rent.query.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RentAdminDetailResponse {
    private final Long rentId;
    private final int maxRecruitmentCount;
    private final int currentRecruitmentCount;
    private final int rentUpCount;
    private final int rentDownCount;
    private final int rentRoundCount;
    private final int additionalDepositCount;
    private final int refundCount;
    @Setter
    private List<RentAdminJoinDetailResponse> rentJoinDetailResponses;

    // for queryDsl
    public RentAdminDetailResponse(
            Long rentId,
            int maxRecruitmentCount,
            int currentRecruitmentCount,
            int rentUpCount,
            int rentDownCount,
            int rentRoundCount,
            int additionalDepositCount,
            int refundCount
    ) {
        this.rentId = rentId;
        this.maxRecruitmentCount = maxRecruitmentCount;
        this.currentRecruitmentCount = currentRecruitmentCount;
        this.rentUpCount = rentUpCount;
        this.rentDownCount = rentDownCount;
        this.rentRoundCount = rentRoundCount;
        this.additionalDepositCount = additionalDepositCount;
        this.refundCount = refundCount;
    }
}