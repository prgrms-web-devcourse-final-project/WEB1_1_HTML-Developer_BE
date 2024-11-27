package com.backend.allreva.concert.command.application;

import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.infra.dto.ConcertCodeResponse;

import java.time.LocalDate;

public interface KopisConcertService {

    ConcertCodeResponse fetchConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate);

    ConcertCodeResponse fetchDailyConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate);

    KopisConcertResponse fetchConcertDetail(String concertCode);
}
