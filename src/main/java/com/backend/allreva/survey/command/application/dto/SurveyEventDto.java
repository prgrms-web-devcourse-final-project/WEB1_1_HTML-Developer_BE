package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonDeserialize(builder = SurveyEventDto.SurveyEventDtoBuilder.class)
public record SurveyEventDto(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("region") Region region,
        @JsonProperty("participationCount") Integer participationCount,
        @JsonProperty("endDate") LocalDate endDate
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static class SurveyEventDtoBuilder {
    }
}