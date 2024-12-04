package com.backend.allreva.survey.command.domain.value;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SurveyEventType {
    CREATE, UPDATE, DELETE
}
