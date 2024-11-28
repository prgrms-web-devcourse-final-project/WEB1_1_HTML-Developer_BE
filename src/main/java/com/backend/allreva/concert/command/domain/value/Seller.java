package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Seller {

    private String relateName;
    private String relateUrl;

    public Seller(String relateName, String relateUrl) {
        this.relateName = relateName;
        this.relateUrl = relateUrl;
    }
}
