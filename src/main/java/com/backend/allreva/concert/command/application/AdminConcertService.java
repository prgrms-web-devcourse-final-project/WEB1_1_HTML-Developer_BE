package com.backend.allreva.concert.command.application;

import com.backend.allreva.common.util.CsvUtil;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminConcertService {
    private final KopisConcertService kopisConcertService;
    private final ConcertRepository concertRepository;

    public void fetchConcertInfoList(LocalDate startDate, LocalDate endDate) {
        List<String> hallCodes = CsvUtil.readConcertHallCodes();
        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchConcertCodes(hallCode,startDate,endDate);
            concertCodes.forEach(concertCode -> {
                KopisConcertResponse response = kopisConcertService.fetchConcertDetail(concertCode);
                concertRepository.save(KopisConcertResponse.toEntity(response));
            });
        });
    }

    public void fetchDailyConcertInfoList(LocalDate today) {
        List<String> hallCodes = CsvUtil.readConcertHallCodes();
        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchDailyConcertCodes(hallCode, today);
            concertCodes.forEach(concertCode -> {
                KopisConcertResponse response = kopisConcertService.fetchConcertDetail(concertCode);
                concertRepository.save(KopisConcertResponse.toEntity(response));
            });
        });
    }
}


