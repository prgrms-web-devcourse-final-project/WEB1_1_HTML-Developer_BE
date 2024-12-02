package com.backend.allreva.survey.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SurveyScheduler {
    private final SurveyCommandRepository surveyCommandRepository;

    @Scheduled(cron = "0 0 0 * * *") //초 분 시 일 월 요일. 매일 0시에 업데이트
    public void closeSurvey() {
        try {
            surveyCommandRepository.closeSurveys(LocalDate.now());
            log.info(" {} :daily survey close complete", LocalDate.now());
        } catch (Exception e) {
            log.error("can't close daily survey. Message: {}", e.getMessage());
        }
    }
}
