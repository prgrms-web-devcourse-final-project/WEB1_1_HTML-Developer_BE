package com.backend.allreva.rent.command.domain.value;


import com.backend.allreva.rent.command.domain.RentFormBoardingDate;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OperationInfo {
    @Column(nullable = false)
    private String boardingArea;

    @OneToMany(mappedBy = "rentForm")
    private List<RentFormBoardingDate> boardingDates = new ArrayList<>();

    @Column(nullable = false)
    private String upTime;

    @Column(nullable = false)
    private String downTime;

    @Embedded
    private Bus bus;

    @Embedded
    private Price price;
}
