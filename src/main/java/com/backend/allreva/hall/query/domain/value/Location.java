package com.backend.allreva.hall.query.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    @Column(name = "concert_hall_longitude")
    private Double longitude;
    @Column(name = "concert_hall_latitude")
    private Double latitude;
    @Column(name = "concert_hall_address")
    private String address;

    public Location(Double longitude, Double latitude, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }
}
