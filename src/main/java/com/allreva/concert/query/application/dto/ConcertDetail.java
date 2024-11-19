package com.allreva.concert.query.application.dto;

import com.allreva.concert.query.domain.value.ConcertInfo;
import com.allreva.concert.query.domain.value.ConcertStatus;
import com.allreva.concert.query.domain.value.IntroduceImage;
import com.allreva.concert.query.domain.value.Seller;
import com.allreva.hall.query.domain.value.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public final class ConcertDetail {

    private IntroduceImage poster;
    private List<IntroduceImage> detailImages;
    private ConcertInfo concertInfo;
    private List<Seller> sellers;
    private ConcertStatus concertStatus;

    private Location location;

    public ConcertDetail(
            IntroduceImage poster,
            List<IntroduceImage> detailImages,
            ConcertInfo concertInfo,
            List<Seller> sellers,
            ConcertStatus concertStatus,
            Location location
    ) {
        this.poster = poster;
        this.detailImages = detailImages;
        this.concertInfo = concertInfo;
        this.sellers = sellers;
        this.concertStatus = concertStatus;
        this.location = location;
    }
}
