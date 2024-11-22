package com.backend.allreva.hall.command.domain;


import com.backend.allreva.hall.command.domain.value.ConcertHallInfo;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConcertHall {

    @Id
    private String id;

    private Double star;

    private String facilityCode;

    @Embedded
    private ConcertHallInfo hallInfo;

    @Embedded
    private ConvenienceInfo convenienceInfo;

    @Embedded
    private Location location;
}
