package com.allreva.concert.query.domain.value;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {

    @Column(name = "concert_title")
    private String title;

    @Column(name = "concert_price")
    private String price;

    @Embedded
    private DateInfo dateInfo;

    public ConcertInfo(String title, String price, DateInfo dateInfo) {
        this.title = title;
        this.price = price;
        this.dateInfo = dateInfo;
    }
}
