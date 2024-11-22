package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    @Column(name = "concert_hall_longitude")
    private Double longitude;
    @Column(name = "concert_hall_latitude")
    private Double latitude;
    @Column(name = "concert_hall_address")
    private String address;
}