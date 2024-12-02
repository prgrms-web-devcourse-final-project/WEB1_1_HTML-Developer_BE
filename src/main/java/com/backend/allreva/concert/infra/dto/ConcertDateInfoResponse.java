package com.backend.allreva.concert.infra.dto;

import java.time.LocalDate;

public interface ConcertDateInfoResponse {
    LocalDate getStartDate();
    LocalDate getEndDate();
}
