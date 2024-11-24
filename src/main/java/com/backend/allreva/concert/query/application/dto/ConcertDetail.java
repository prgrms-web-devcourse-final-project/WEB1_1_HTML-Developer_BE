package com.backend.allreva.concert.query.application.dto;


import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.value.Seller;

import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public final class ConcertDetail {

    private Image poster;
    private List<Image> detailImages;
    private ConcertInfo concertInfo;
    private List<Seller> sellers;

    private String hallCode;
    private String hallName;
    private Integer seatScale;
    private ConvenienceInfo convenienceInfo;
    private String address;

    @Builder
    public ConcertDetail(
            Image poster,
            List<Image> detailImages,
            ConcertInfo concertInfo,
            List<Seller> sellers,

            String hallCode,
            String hallName,
            Integer seatScale,
            ConvenienceInfo convenienceInfo,
            String address
    ) {
        this.poster = poster;
        this.detailImages = detailImages;
        this.concertInfo = concertInfo;
        this.sellers = sellers;

        this.hallCode = hallCode;
        this.hallName = hallName;
        this.seatScale = seatScale;
        this.convenienceInfo = convenienceInfo;
        this.address = address;
    }
}
