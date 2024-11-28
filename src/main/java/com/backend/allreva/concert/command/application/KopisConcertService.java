package com.backend.allreva.concert.command.application;

import com.backend.allreva.concert.infra.dto.KopisConcertResponse;

import java.time.LocalDate;
import java.util.List;

public interface KopisConcertService {

    List<String> fetchConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate);

    List<String> fetchDailyConcertCodes(String hallCode, LocalDate today);

    KopisConcertResponse fetchConcertDetail(String concertCode);
}
