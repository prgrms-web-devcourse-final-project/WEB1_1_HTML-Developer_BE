package com.backend.allreva.hall.command.domain;


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
    // fcltyName + prfplcName
    private String name;
    private Integer seatScale;
    private Double star;

    @Embedded
    private ConvenienceInfo convenienceInfo;
    @Embedded
    private Location location;
}
