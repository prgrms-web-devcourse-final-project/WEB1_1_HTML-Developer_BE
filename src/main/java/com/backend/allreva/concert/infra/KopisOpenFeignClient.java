package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.command.application.KopisConcertService;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import com.backend.allreva.concert.infra.dto.KopisConcertCodeResponse.Db;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class KopisOpenFeignClient implements KopisConcertService {
    private final OpenFeignClient openFeignClient;

    @Override
    public List<String> fetchConcertCodes(String hallCode, LocalDate startDate, LocalDate endDate) {
        List<Db> dbList = openFeignClient.fetchConcertCodes(hallCode, startDate, endDate, null).getDbList();
        List<String> concertCodes = new ArrayList<>();
        for (Db db : dbList) {
            concertCodes.add(db.getId());
        }
        return concertCodes;
    }

    @Override
    public List<String> fetchDailyConcertCodes(String hallCode, LocalDate today) {

        List<Db> dbList = openFeignClient.fetchConcertCodes(hallCode, null, null, today).getDbList();
        List<String> concertCodes = new ArrayList<>();
        for (Db db : dbList) {
            concertCodes.add(db.getId());
        }
        return concertCodes;
    }

    @Override
    public KopisConcertResponse fetchConcertDetail(String concertCode) {
        return openFeignClient.fetchConcertDetail(concertCode);
    }
}
