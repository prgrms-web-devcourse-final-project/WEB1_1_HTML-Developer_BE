package com.backend.allreva.concert.fixture;

import com.backend.allreva.hall.command.domain.ConcertHall;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ConcertHallFixture {

    public static ConcertHall createConcertHallFixture() {
        return ConcertHall.builder()
                .id("FC000001-01")
                .seatScale(10000)
                .convenienceInfo(ConvenienceInfo.builder()
                        .build())
                .name("Sample Hall")
                .location(Location.builder()
                        .address("Sample Address")
                        .latitude(37.123456)
                        .longitude(127.123456)
                        .build())
                .build();
    }
}
