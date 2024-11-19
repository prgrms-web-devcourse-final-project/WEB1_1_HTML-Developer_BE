package com.kwanse.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Seller {
    @Column(name = "concert_relate_name")
    private String relateName;
    @Column(name = "concert_relate_url")
    private String relateUrl;

}
