package com.backend.allreva.concert.command.application;

import com.backend.allreva.common.converter.DateConverter;
import com.backend.allreva.common.util.CsvUtil;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminConcertService {
    private final KopisConcertService kopisConcertService;
    private final ConcertRepository concertRepository;

    public void fetchConcertInfoList(int year, int month) {
        List<String> hallCodes = CsvUtil.readConcertHallCodes();

        // 해당 월의 첫째 날과 마지막 날
        String[] dates = getStartAndEndDate(year, month);
        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchConcertCodes(
                    hallCode, dates[0], dates[1]);

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

        LocalDate date = LocalDate.now();
        String[] dates = getStartAndEndDate(date.getYear(), date.getMonthValue());

        hallCodes.parallelStream().forEach(hallCode -> {
            List<String> concertCodes = kopisConcertService.fetchDailyConcertCodes(hallCode, dates[0], dates[1], today);
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

    private static String[] getStartAndEndDate(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        String startDate = DateConverter.convertToyyyyMMdd(yearMonth.atDay(1));
        String endDate = DateConverter.convertToyyyyMMdd(yearMonth.atEndOfMonth());
        return new String[]{startDate, endDate};
    }
}
