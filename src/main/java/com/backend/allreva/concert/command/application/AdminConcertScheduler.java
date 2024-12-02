package com.backend.allreva.concert.command.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminConcertScheduler {
    private final AdminConcertService adminConcertService;
   @Scheduled(cron = "0 0 4 * * *") //초 분 시 일 월 요일. 매일 새벽 4시에 업데이트
    public void fetchDailyConcertInfoList() {
        try {
            LocalDate today = LocalDate.now();
            String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            adminConcertService.fetchDailyConcertInfoList(formattedDate);
            log.info(" {} :daily concert info update complete", formattedDate);
        } catch (Exception e) {
            log.error("can't update daily concert info. Message: {}", e.getMessage());
        }
    }
}
