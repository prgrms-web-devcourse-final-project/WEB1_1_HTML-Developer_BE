package com.backend.allreva.hall.query.application.dto;


import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertHallDetail {

    private String fcltyName;
    private ConvenienceInfo convenienceInfo;
    private Location location;

    List<String> prfplcNames;
    private List<Integer> seatScales;
    private List<Double> stars;
}
