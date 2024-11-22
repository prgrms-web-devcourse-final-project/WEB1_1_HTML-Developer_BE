package com.backend.allreva.rent.command.domain.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price {
    private Integer roundPrice;
    private Integer upTimePrice;
    private Integer downTimePrice;
}
