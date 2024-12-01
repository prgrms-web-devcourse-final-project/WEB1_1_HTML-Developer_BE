package com.backend.allreva.hall.command.domain;


import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConcertHall {

    @Id
    private String id;

    @Column(nullable = false)
    private String name; // fcltyName + prfplcName
    private int seatScale;
    private double star;

    @Embedded
    private ConvenienceInfo convenienceInfo;
    @Embedded
    private Location location;


    @Builder
    public ConcertHall(
            String id,
            String name,
            int seatScale,
            ConvenienceInfo convenienceInfo,
            Location location
    ) {
        this.id = id;
        this.name = name;
        this.seatScale = seatScale;
        this.star = 0.0;
        this.convenienceInfo = convenienceInfo;
        this.location = location;
    }
}
