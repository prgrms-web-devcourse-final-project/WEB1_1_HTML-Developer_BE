package com.backend.allreva.surveyJoin.command.domain;

import com.backend.allreva.surveyJoin.command.domain.SurveyJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJoinCommandRepository extends JpaRepository<SurveyJoin, Long> {
}
