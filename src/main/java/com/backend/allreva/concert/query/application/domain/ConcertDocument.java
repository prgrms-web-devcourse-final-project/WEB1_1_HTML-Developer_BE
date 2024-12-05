package com.backend.allreva.concert.query.application.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "concerts")
@Setting(settingPath = "elasticsearch/mappings/es-settings.json")
@Mapping(mappingPath = "elasticsearch/mappings/concert-mapping.json")
@Getter
@ToString
public class ConcertDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title", analyzer = "korean_mixed")
    private String title;

    @Field(type = FieldType.Keyword, name = "concert_code")
    private String concertCode;

    @Field(type = FieldType.Keyword, name = "hall_code")
    private String hallCode;

    @Field(type = FieldType.Keyword, name = "time_table")
    private String timeTable;

    @Field(type = FieldType.Keyword, name = "host")
    private String host;

    @Field(type = FieldType.Keyword, name = "prfstate")
    private String prfState;

    @Field(type = FieldType.Text, name = "price")
    private String price;

    @Field(type = FieldType.Keyword, name = "poster")
    private String poster;

    @Field(type = FieldType.Long, name = "view_count")
    private Long viewCount;

    @Field(type = FieldType.Boolean, name = "concert_hall_cafe")
    private Boolean concertHallCafe;

    @Field(type = FieldType.Boolean, name = "concert_hall_elev_barrier")
    private Boolean concertHallElevBarrier;

    @Field(type = FieldType.Boolean, name = "concert_hall_park_barrier")
    private Boolean concertHallParkBarrier;

    @Field(type = FieldType.Boolean, name = "concert_hall_parking")
    private Boolean concertHallParking;

    @Field(type = FieldType.Boolean, name = "concert_hall_rest_barrier")
    private Boolean concertHallRestBarrier;

    @Field(type = FieldType.Boolean, name = "concert_hall_restaurant")
    private Boolean concertHallRestaurant;

    @Field(type = FieldType.Boolean, name = "concert_hall_runw_barrier")
    private Boolean concertHallRunwBarrier;

    @Field(type = FieldType.Boolean, name = "concert_hall_store")
    private Boolean concertHallStore;

    @Field(type = FieldType.Keyword, name = "concert_hall_name")
    private String concertHallName;

    @Field(type = FieldType.Integer, name = "concert_hall_seat_scale")
    private Integer concertHallSeatScale;

    @Field(type = FieldType.Text, name = "concert_hall_address", analyzer = "korean_mixed")
    private String concertHallAddress;

    @Field(type = FieldType.Double, name = "concert_hall_latitude")
    private Double concertHallLatitude;

    @Field(type = FieldType.Double, name = "concert_hall_longitude")
    private Double concertHallLongitude;

    @Field(type = FieldType.Double, name = "star")
    private Double star;

    @Field(type = FieldType.Keyword, name = "image_urls")
    private List<String> imageUrls;

    @Field(type = FieldType.Keyword, name = "seller_info")
    private List<String> sellerInfo;

    @Field(type = FieldType.Date, name = "stdate")
    private LocalDate stDate;

    @Field(type = FieldType.Date, name = "eddate")
    private LocalDate edDate;

    @Field(type = FieldType.Keyword, name = "episodes")
    private List<String> episodes;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'||strict_date_optional_time||epoch_millis")
    private LocalDateTime stdate;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'||strict_date_optional_time||epoch_millis")
    private LocalDateTime eddate;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'||strict_date_optional_time||epoch_millis")
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'||strict_date_optional_time||epoch_millis")
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'||strict_date_optional_time||epoch_millis")
    private LocalDateTime deletedAt;

    public void updateViewCount(final Long viewCount) {
        this.viewCount = viewCount;
    }

    public void intiViewCount() {
        viewCount = 0L;
    }
}
