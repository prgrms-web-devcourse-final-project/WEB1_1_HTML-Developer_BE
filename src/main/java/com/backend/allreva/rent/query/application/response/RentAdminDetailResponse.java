package com.backend.allreva.rent.query.application.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RentAdminDetailResponse {
    @JsonUnwrapped
    private final RentAdminSummaryResponse rentAdminSummaryResponse;
    @JsonUnwrapped
    private final RentJoinCountResponse rentJoinCountResponse;
    private final List<RentJoinDetailResponse> rentJoinDetailResponses;
}