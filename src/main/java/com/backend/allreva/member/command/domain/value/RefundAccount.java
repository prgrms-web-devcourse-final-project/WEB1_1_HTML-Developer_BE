package com.backend.allreva.member.command.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundAccount {

    private String bank;
    private String number;

    @Builder
    private RefundAccount(final String bank, final String number) {
        this.bank = bank;
        this.number = number;
    }
}
