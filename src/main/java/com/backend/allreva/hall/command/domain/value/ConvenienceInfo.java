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

    @Column(name = "concert_hall_parking")
    private Boolean parking;
    @Column(name = "concert_hall_restaurant")
    private Boolean restaurant;
    @Column(name = "concert_hall_cafe")
    private Boolean cafe;
    @Column(name = "concert_hall_store")
    private Boolean store;
    @Column(name = "concert_hall_park_barrier")
    private Boolean parkBarrier;
    @Column(name = "concert_hall_rest_barrier")
    private Boolean restBarrier;
    @Column(name = "concert_hall_elev_barrier")
    private Boolean elevBarrier;
    @Column(name = "concert_hall_runw_barrier")
    private Boolean runwBarrier;
}