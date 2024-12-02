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

    @Column(nullable = false)
    private String concertCode;
    @Column(nullable = false)
    private String hallCode;

    @Builder
    private Code(
            final String concertCode,
            final String hallCode
    ) {
        this.concertCode = concertCode;
        this.hallCode = hallCode;
    }
}
