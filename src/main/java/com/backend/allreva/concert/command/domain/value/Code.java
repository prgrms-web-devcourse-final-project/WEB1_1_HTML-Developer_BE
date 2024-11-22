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

    @Column(name = "concert_code")
    private String concertCode;

    @Column(name = "concert_hall_code")
    private String hallCode;

    @Column(name = "concert_facility_code")
    private String facilityCode;

    @Builder
    public Code(String concertCode, String hallCode, String facilityCode) {
        this.concertCode = concertCode;
        this.hallCode = hallCode;
        this.facilityCode = facilityCode;
    }
}
