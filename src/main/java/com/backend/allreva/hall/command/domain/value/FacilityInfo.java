package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FacilityInfo {

    private String fcltyCode;
    private String fcltyName;

    @Embedded
    private ConvenienceInfo convenienceInfo;
    @Embedded
    private Location location;

    @Builder
    public FacilityInfo(
            String fcltyCode,
            String fcltyName,
            ConvenienceInfo convenienceInfo,
            Location location
    ) {
        this.fcltyCode = fcltyCode;
        this.fcltyName = fcltyName;
        this.convenienceInfo = convenienceInfo;
        this.location = location;
    }
}
