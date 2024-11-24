package com.backend.allreva.hall.query.application.dto;

import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.Getter;

@Getter
public class ConcertHallDetail {

    private String name;
    private Integer seatScale;
    private Double star;

    private ConvenienceInfo convenienceInfo;
    private Location location;

    public ConcertHallDetail(
            String name,
            Integer seatScale,
            Double star,
            ConvenienceInfo convenienceInfo,
            Location location
    ) {
        this.name = name;
        this.seatScale = seatScale;
        this.star = star;
        this.convenienceInfo = convenienceInfo;
        this.location = location;
    }
}
