package com.backend.allreva.survey.command.application.request;

import jakarta.validation.constraints.NotNull;

public record SurveyIdRequest(@NotNull  Long surveyId) {
}
