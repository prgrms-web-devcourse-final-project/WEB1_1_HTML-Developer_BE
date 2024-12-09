package com.backend.allreva.survey_join.command.domain;

import com.backend.allreva.survey_join.infra.rdb.SurveyJoinDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJoinRepository extends JpaRepository<SurveyJoin, Long>, SurveyJoinDslRepository {
}
