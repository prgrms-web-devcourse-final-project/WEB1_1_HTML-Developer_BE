package com.backend.allreva.rent.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OperationInfo {
    @Column(nullable = false)
    private String boardingArea;

    @Column(nullable = false)
    private String boardingDate;

    @Column(nullable = false)
    private String upTime;

    @Column(nullable = false)
    private String downTime;

    @Embedded
    private Bus bus;

    @Embedded
    private Price price;
}