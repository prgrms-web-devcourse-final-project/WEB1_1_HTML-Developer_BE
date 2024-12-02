package com.backend.allreva.concert.command.domain.value;

import com.backend.allreva.concert.exception.ConcertStatusNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConcertStatus {
    COMPLETED("공연완료"),
    IN_PROGRESS("공연중"),
    SCHEDULED("공연예정");

    private final String korean;

    public static ConcertStatus convertToConcertStatus(final String korean) {
        for (ConcertStatus status : ConcertStatus.values()) {
            if (status.getKorean().equals(korean)) {
                return status;
            }
        }
        throw new ConcertStatusNotFoundException();
    }
}
