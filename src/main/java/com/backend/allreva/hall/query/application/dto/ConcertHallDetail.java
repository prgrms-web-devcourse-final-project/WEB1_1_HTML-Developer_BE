package com.backend.allreva.hall.query.application.dto;

import com.backend.allreva.hall.command.domain.value.ConcertHallInfo;
import com.backend.allreva.hall.command.domain.value.FacilityInfo;
import lombok.Getter;

@Getter
public class ConcertHallDetail {

    private FacilityInfo facilityInfo;
    private ConcertHallInfo concertHallInfo;

    public ConcertHallDetail(
            FacilityInfo facilityInfo,
            ConcertHallInfo concertHallInfo)
    {
        this.facilityInfo = facilityInfo;
        this.concertHallInfo = concertHallInfo;
    }
}
