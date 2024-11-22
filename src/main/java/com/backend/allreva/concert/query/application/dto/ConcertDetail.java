package com.backend.allreva.concert.query.application.dto;


import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.IntroduceImage;
import com.backend.allreva.concert.command.domain.value.Seller;

import com.backend.allreva.hall.command.domain.value.ConcertHallInfo;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public final class ConcertDetail {

    private IntroduceImage poster;
    private List<IntroduceImage> detailImages;

    private ConcertInfo concertInfo;
    private List<Seller> sellers;

    private Code code;

    private ConcertHallInfo concertHallInfo;
    private ConvenienceInfo convenienceInfo;
    private Location location;

    @Builder
    public ConcertDetail(
            Concert concert,
            ConcertHallInfo concertHallInfo,
            ConvenienceInfo convenienceInfo,
            Location location
    ) {
        this.poster = concert.getPoster();
        this.detailImages = concert.getDetailImages();
        this.concertInfo = concert.getConcertInfo();
        this.sellers = concert.getSellers();
        this.code = concert.getCode();
        this.convenienceInfo = convenienceInfo;
        this.concertHallInfo = concertHallInfo;
        this.location = location;
    }
}
