package com.backend.allreva.hall.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertHallInfo {

    private String fcltyName;
    private String prfplcName;
    private Integer seatScale;
}