package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private Double longitude;
    private Double latitude;

    @Column(nullable = false)
    private String address;

    @Builder
    private Location(
            final Double longitude,
            final Double latitude,
            final String address
    ) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }
}