package com.backend.allreva.concert.command.application;

import com.backend.allreva.common.util.CsvUtil;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminConcertService {
    private final KopisConcertService kopisConcertService;
    private final ConcertRepository concertRepository;

    @Value("${public-data.kopis.stdate}")
    private String startDate;

    @Value("${public-data.kopis.eddate}")
    private String endDate;


    public void fetchConcertInfoList() {
        List<String> hallCodes = CsvUtil.readConcertHallCodes();
        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchConcertCodes(hallCode, startDate, endDate);

            concertCodes.forEach(concertCode -> {
                KopisConcertResponse response = kopisConcertService.fetchConcertDetail(concertCode);
                concertRepository.save(KopisConcertResponse.toEntity(hallCode, response));
                log.info("All concert details processed for hall Code: {}", hallCode);
            });
        });
    }

    // 매일 업데이트 함수
    public void fetchDailyConcertInfoList(String today) {
        List<String> hallCodes = CsvUtil.readConcertHallCodes();

        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchDailyConcertCodes(hallCode, startDate, endDate, today);

            concertCodes.forEach(concertCode -> {
                KopisConcertResponse response = kopisConcertService.fetchConcertDetail(concertCode);
                processConcertUpdateOrInsert(hallCode, response);
                log.info("All concert details updated for hall Code: {}", hallCode);
            });
        });
    }

    // 공연 정보 업데이트 혹은 새로 추가
    private void processConcertUpdateOrInsert(String hallCode, KopisConcertResponse response) {
        String tempConcertCode = response.getDb().getConcertCode();
        boolean isExist = concertRepository.existsByConcertCode(tempConcertCode);

        if (isExist) {
            updateConcert(hallCode, response, tempConcertCode);
        } else {
            concertRepository.save(KopisConcertResponse.toEntity(hallCode, response));
        }
    }

    // 기존 공연 정보 업데이트
    private void updateConcert(String hallCode, KopisConcertResponse response, String concertCode) {
        Concert existingConcert = concertRepository.findByConcertCode(concertCode);
        existingConcert.updateFrom(hallCode, response.getDb());
        concertRepository.save(existingConcert);
    }
}
