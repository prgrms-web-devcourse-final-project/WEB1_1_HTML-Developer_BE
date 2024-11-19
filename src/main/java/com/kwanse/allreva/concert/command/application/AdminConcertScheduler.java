package com.kwanse.allreva.concert.command.application;


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
    @Scheduled(cron = "0 0 4 * * *") //초 분 시 일 월 요일
    public void saveBestSeller() {
        try {
            adminConcertService.fetchDailyConcertInfoList();
            LocalDate today = LocalDate.now();
            String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            log.info(" {} :daily concert info update complete", formattedDate);
        } catch (Exception e) {
            log.info("can't update daily concert info. Message: {}", e.getMessage());
        }
    }
}
