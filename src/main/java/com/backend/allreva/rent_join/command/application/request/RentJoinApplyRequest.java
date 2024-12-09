package com.backend.allreva.rent_join.command.application.request;

import com.backend.allreva.rent_join.command.domain.RentJoin;
import com.backend.allreva.rent_join.command.domain.value.BoardingType;
import com.backend.allreva.rent_join.command.domain.value.Depositor;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDate;

public record RentJoinApplyRequest(
        Long rentId,
        String depositorName,
        String depositorTime,
        String phone,
        int passengerNum,
        BoardingType boardingType,
        RefundType refundType,
        String refundAccount,
        LocalDate boardingDate
) {

    public RentJoin toEntity(final Long memberId) {
        return RentJoin.builder()
                .rentId(rentId)
                .memberId(memberId)
                .depositor(Depositor.builder()
                        .depositorName(depositorName)
                        .depositorTime(depositorTime)
                        .phone(phone)
                        .build())
                .boardingType(boardingType)
                .refundType(refundType)
                .refundAccount(refundAccount)
                .boardingDate(boardingDate)
                .build();
    }
}
