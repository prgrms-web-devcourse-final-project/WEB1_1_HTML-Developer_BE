package com.backend.allreva.search.query.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Document(indexName = "survey")
@Setting(settingPath = "elasticsearch/mappings/es-settings.json")
@Mapping(mappingPath = "elasticsearch/mappings/survey-mapping.json")
@Getter
@ToString
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
}
