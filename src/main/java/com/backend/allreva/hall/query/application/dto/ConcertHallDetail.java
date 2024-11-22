package com.backend.allreva.hall.query.application.dto;


import com.backend.allreva.hall.command.domain.value.ConcertHallInfo;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.Getter;

@Getter
public class ConcertHallDetail {

    private ConcertHallInfo concertHallInfo;
    private ConvenienceInfo convenienceInfo;
    private Location location;
}
