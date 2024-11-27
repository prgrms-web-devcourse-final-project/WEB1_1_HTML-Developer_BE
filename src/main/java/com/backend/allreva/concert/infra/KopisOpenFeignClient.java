package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.command.application.KopisConcertService;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.infra.dto.ConcertCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KopisOpenFeignClient implements KopisConcertService {
    private final OpenFeignClient openFeignClient;

    @Override
    public ConcertCodeResponse fetchConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate) {
        return openFeignClient.fetchConcertCodes(hallCode, startDate, endDate, null);

    }

    @Override
    public ConcertCodeResponse fetchDailyConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate) {

        return openFeignClient.fetchConcertCodes(hallCode, startDate, endDate, LocalDate.now());
    }

    @Override
    public KopisConcertResponse fetchConcertDetail(String concertCode) {
        return openFeignClient.fetchConcertDetail(concertCode);
    }
}
