package com.backend.allreva.survey.query.application.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Document(indexName = "survey")
@Setting(settingPath = "elasticsearch/mappings/es-settings.json")
@Mapping(mappingPath = "elasticsearch/mappings/survey-mapping.json")
@Getter
@ToString
@Builder
@AllArgsConstructor
public class SurveyDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title", analyzer = "korean_mixed")
    private String title;

    @Field(type = FieldType.Keyword, name = "region")
    private String region;

    @Field(type = FieldType.Keyword, name = "participation_count")
    private Integer participationCount;

    @Field(type = FieldType.Date, name = "eddate")
    private LocalDate edDate;

    public void updateSurveyDocument(
            final String title,
            final String region,
            final LocalDate edDate
    ){
        this.title = title;
        this.region = region;
        this.edDate = edDate;
    }

    public void updateParticipationCount(final int participationCount) {
        this.participationCount += participationCount;
    }
}
