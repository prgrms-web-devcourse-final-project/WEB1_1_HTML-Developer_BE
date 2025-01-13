package com.backend.allreva.hall.query.application.response;

import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.query.domain.ConcertHallDocument;

public record ConcertHallThumbnail(
        String id,
        String name,
        String address,
        int seatScale,
        ConvenienceInfo convenienceInfo

) {
    public static ConcertHallThumbnail from(final ConcertHallDocument document) {
        ConvenienceInfo convenienceInfo = ConvenienceInfo.builder()
                .hasParkingLot(document.getParking())
                .hasRestaurant(document.getRestaurant())
                .hasCafe(document.getCafe())
                .hasStore(document.getStore())
                .hasDisabledParking(document.getParkBarrier())
                .hasDisabledToilet(document.getRestBarrier())
                .hasElevator(document.getElevBarrier())
                .hasRunway(document.getRunwBarrier())
                .build();
        return new ConcertHallThumbnail(
                document.getId(),
                document.getName(),
                document.getAddress(),
                document.getSeatScale(),
                convenienceInfo
        );
    }
}