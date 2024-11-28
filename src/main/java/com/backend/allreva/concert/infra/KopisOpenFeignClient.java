package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.command.application.KopisConcertService;
import com.backend.allreva.concert.infra.dto.KopisConcertCodeResponse.Db;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class KopisOpenFeignClient implements KopisConcertService {
    private final OpenFeignClient openFeignClient;

    @Override
    public List<String> fetchConcertCodes(String hallCode, String startDate, String endDate) {
        return openFeignClient.fetchConcertCodes(hallCode, startDate, endDate, null)
                .getDbList()
                .stream()
                .map(Db::getId)
                .toList();
    }

    @Override
    public List<String> fetchDailyConcertCodes(String hallCode, String startDate, String endDate, String today) {
        return openFeignClient.fetchConcertCodes(hallCode, startDate, endDate, today)
                .getDbList()
                .stream()
                .map(Db::getId)
                .toList();
    }

    @Override
    public KopisConcertResponse fetchConcertDetail(String concertCode) {
        return openFeignClient.fetchConcertDetail(concertCode);
    }
}
