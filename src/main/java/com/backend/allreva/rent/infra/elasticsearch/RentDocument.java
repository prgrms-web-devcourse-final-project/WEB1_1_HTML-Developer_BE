package com.backend.allreva.rent.infra.elasticsearch;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Document(indexName = "rent")
@Getter
@Setting(settingPath = "elasticsearch/mappings/es-settings.json")
@Mapping(mappingPath = "elasticsearch/mappings/rent-mapping.json")
public class RentDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title", analyzer = "korean_mixed")
    private String title;

    @Field(type = FieldType.Keyword, name = "boarding_area")
    private String boardingArea;

    @Field(type = FieldType.Keyword, name = "image")
    private String imageUrl;

    @Field(type = FieldType.Date, name = "eddate")
    private LocalDate edDate;

    @Builder
    public RentDocument(String id, String title, String boardingArea, String imageUrl, LocalDate edDate) {
        this.id = id;
        this.title = title;
        this.boardingArea = boardingArea;
        this.imageUrl = imageUrl;
        this.edDate = edDate;
    }
}
