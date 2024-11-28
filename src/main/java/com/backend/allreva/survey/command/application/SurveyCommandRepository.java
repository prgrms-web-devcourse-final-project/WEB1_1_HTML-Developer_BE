package com.backend.allreva.survey.command.application;

import com.backend.allreva.survey.command.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyCommandRepository extends JpaRepository<Survey, Long> {
}
