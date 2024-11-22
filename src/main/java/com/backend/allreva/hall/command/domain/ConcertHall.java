package com.backend.allreva.hall.command.domain;


import com.backend.allreva.hall.command.domain.value.ConcertHallInfo;
import com.backend.allreva.hall.command.domain.value.FacilityInfo;
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

    @Embedded
    private FacilityInfo facilityInfo;
    @Embedded
    private ConcertHallInfo hallInfo;

}
