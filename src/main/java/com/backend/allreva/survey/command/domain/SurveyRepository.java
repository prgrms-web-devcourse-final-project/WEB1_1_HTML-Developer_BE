package com.backend.allreva.survey.command.domain;

import com.backend.allreva.survey.infra.rdb.SurveyDslRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SurveyRepository extends JpaRepository<Survey, Long>, SurveyDslRepository {

    @Modifying
    @Query("UPDATE Survey s SET s.isClosed = true WHERE s.endDate < :today")
    void closeSurveys(@Param("today") LocalDate today);
}
