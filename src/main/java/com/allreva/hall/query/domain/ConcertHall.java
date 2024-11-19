package com.allreva.hall.query.domain;

import com.allreva.hall.query.domain.value.ConcertHallInfo;
import com.allreva.hall.query.domain.value.ConvenienceInfo;
import com.allreva.hall.query.domain.value.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConcertHall {

    @Id
    private String id;

    private Double star;

    @Embedded
    private ConcertHallInfo hallInfo;
    @Embedded
    private ConvenienceInfo convenienceInfo;
    @Embedded
    private Location location;
}
