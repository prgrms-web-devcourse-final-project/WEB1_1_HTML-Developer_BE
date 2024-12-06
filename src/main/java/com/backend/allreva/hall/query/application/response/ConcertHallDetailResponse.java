package com.backend.allreva.hall.query.application.response;

import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;

public record ConcertHallDetailResponse(

        String name,
        Integer seatScale,
        Double star,

        ConvenienceInfo convenienceInfo,
        Location location

) {

}
