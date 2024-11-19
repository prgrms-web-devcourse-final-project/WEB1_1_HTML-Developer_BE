package com.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Seller {

    @Column(name = "concert_relate_name")
    private String relateName;
    @Column(name = "concert_relate_url")
    private String relateUrl;
}
