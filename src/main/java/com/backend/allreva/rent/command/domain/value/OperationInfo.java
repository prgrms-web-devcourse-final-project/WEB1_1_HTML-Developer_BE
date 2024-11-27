package com.backend.allreva.rent.command.domain.value;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OperationInfo {
    @Column(nullable = false)
    private String boardingArea;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "rent_form_boarding_date",
            joinColumns = @JoinColumn(name = "id", nullable = false)
    )
    private List<LocalDate> boardingDates;

    @Column(nullable = false)
    private String upTime;

    @Column(nullable = false)
    private String downTime;

    @Embedded
    private Bus bus;

    @Embedded
    private Price price;
}
