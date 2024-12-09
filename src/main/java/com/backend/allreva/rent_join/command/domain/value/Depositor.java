package com.backend.allreva.rent_join.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Depositor {
    @Column(nullable = false)
    private String depositorName;

    @Column(nullable = false)
    private String depositorTime;

    @Column(nullable = false)
    private String phone;
}
