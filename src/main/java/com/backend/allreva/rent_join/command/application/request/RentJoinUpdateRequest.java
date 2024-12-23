package com.backend.allreva.rent_join.command.application.request;

import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RentJoinUpdateRequest(
        @NotNull
        Long rentJoinId,
        @NotNull
        LocalDate boardingDate,
        @NotNull
        BoardingType boardingType,
        @NotNull
        @Min(value = 1, message = "탑승 인원 수는 1명 이상이어야 합니다.")
        int passengerNum,
        @NotNull
        String depositorName,
        @NotNull
        String depositorTime,
        @NotNull
        String phone,
        @NotNull
        RefundType refundType,
        @NotNull
        String refundAccount
) {

}
