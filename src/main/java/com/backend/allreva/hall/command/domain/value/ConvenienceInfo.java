package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConvenienceInfo {

    private boolean parking;
    private boolean restaurant;
    private boolean cafe;
    private boolean store;

    private boolean parkBarrier;
    private boolean restBarrier;
    private boolean elevBarrier;
    private boolean runwBarrier;
}