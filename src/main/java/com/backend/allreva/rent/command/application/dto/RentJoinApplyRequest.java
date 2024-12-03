package com.backend.allreva.rent.command.application.dto;

import com.backend.allreva.rent.command.domain.RentJoin;
import com.backend.allreva.rent.command.domain.value.BoardingType;
import com.backend.allreva.rent.command.domain.value.Depositor;
import com.backend.allreva.rent.command.domain.value.RefundType;
import java.time.LocalDate;

public record RentJoinApplyRequest(
        Long rentId,
        String depositorName,
        String depositorTime,
        String phone,
        String passengerNum,
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
