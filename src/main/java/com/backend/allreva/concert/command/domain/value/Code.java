package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Code {

    private String concertCode;
    private String hallCode;
    private String fcltyCode;

    @Builder
    public Code(String concertCode, String hallCode, String facilityCode) {
        this.concertCode = concertCode;
        this.hallCode = hallCode;
        this.fcltyCode = facilityCode;
    }
}
