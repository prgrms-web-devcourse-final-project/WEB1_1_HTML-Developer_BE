package com.backend.allreva.member.command.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundAccountInfo {

    private String bank;
    private String number;

    @Builder
    private RefundAccountInfo(String bank, String number) {
        this.bank = bank;
        this.number = number;
    }
}
