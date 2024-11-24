package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

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
