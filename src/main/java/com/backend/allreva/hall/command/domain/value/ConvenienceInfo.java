package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConvenienceInfo {

    private Boolean parking;
    private Boolean restaurant;
    private Boolean cafe;
    private Boolean store;

    private Boolean parkBarrier;
    private Boolean restBarrier;
    private Boolean elevBarrier;
    private Boolean runwBarrier;
}