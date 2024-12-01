package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConvenienceInfo {

    @Column(name = "parking")
    private boolean hasParkingLot;
    @Column(name = "restaurant")
    private boolean hasRestaurant;
    @Column(name = "cafe")
    private boolean hasCafe;
    @Column(name = "store")
    private boolean hasStore;

    @Column(name = "park_barrier")
    private boolean hasDisabledParking;
    @Column(name = "rest_barrier")
    private boolean hasDisabledToilet;
    @Column(name = "elev_barrier")
    private boolean hasElevator;
    @Column(name = "runw_barrier")
    private boolean hasRunway;

    @Builder
    private ConvenienceInfo(
            final boolean hasParkingLot,
            final boolean hasRestaurant,
            final boolean hasCafe,
            final boolean hasStore,
            final boolean hasDisabledParking,
            final boolean hasDisabledToilet,
            final boolean hasElevator,
            final boolean hasRunway
    ) {
        this.hasParkingLot = hasParkingLot;
        this.hasRestaurant = hasRestaurant;
        this.hasCafe = hasCafe;
        this.hasStore = hasStore;
        this.hasDisabledParking = hasDisabledParking;
        this.hasDisabledToilet = hasDisabledToilet;
        this.hasElevator = hasElevator;
        this.hasRunway = hasRunway;
    }
}