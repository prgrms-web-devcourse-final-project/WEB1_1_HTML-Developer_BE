package com.backend.allreva.concert.query.application.dto;


import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.Seller;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public final class ConcertDetailResponse {

    public static final ConcertDetailResponse EMPTY = new ConcertDetailResponse();

    private Image poster;
    private Set<Image> detailImages;
    private ConcertInfo concertInfo;
    private Set<Seller> sellers;

    private String hallCode;
    private String hallName;
    private Integer seatScale;
    private ConvenienceInfo convenienceInfo;
    private String address;

    @Builder
    public ConcertDetailResponse(
            final Image poster,
            final Set<Image> detailImages,
            final ConcertInfo concertInfo,
            final Set<Seller> sellers,

            final String hallCode,
            final String hallName,
            final Integer seatScale,
            final ConvenienceInfo convenienceInfo,
            final String address
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
