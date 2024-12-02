package com.backend.allreva.survey.command.application.dto;

import jakarta.validation.constraints.NotNull;

public record SurveyIdRequest(@NotNull  Long surveyId) {
}
