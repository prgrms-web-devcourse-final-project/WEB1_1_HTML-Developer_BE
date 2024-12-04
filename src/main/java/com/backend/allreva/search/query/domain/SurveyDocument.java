package com.backend.allreva.search.query.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "survey")
@Getter
@ToString
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
