package com.backend.allreva.concert.command.application;

import com.backend.allreva.concert.infra.dto.KopisConcertResponse;

import java.util.List;

public interface KopisConcertService {

    List<String> fetchConcertCodes(String hallCode, String startDate, String endDate);

    List<String> fetchDailyConcertCodes(String hallCode, String startDate, String endDate, String today);

    KopisConcertResponse fetchConcertDetail(String concertCode);
}
