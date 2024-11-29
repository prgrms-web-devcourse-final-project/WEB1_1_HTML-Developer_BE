package com.backend.allreva.rent.query.application.dto;

import com.backend.allreva.common.converter.DataConverter;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class RentFormDetailResponse {
    private final String concertName; // x -> 대신 concert랑 조인해서 concert name
    private final String imageUrl;
    private final String title;
    private final String artistName;
    private final Region region;
    private final String boardingArea; // 상행 지역
    private final String dropOffArea; // 하행 지역 -> concerthall code 조인
    private final String upTime;
    private final String downTime;
    private final List<String> rentBoardingDates;
    private final BusSize busSize;
    private final BusType busType;
    private final int maxPassenger;
    private final int roundPrice;
    private final int upTimePrice;
    private final int downTimePrice;
    private final int recruitmentCount;
    private final LocalDate endDate;
    private final String chatUrl; // 지금은 필요없을듯 일단은 넣어놓자.
    private final RefundType refundType;
    private final String information;

    // for querydsl
    public RentFormDetailResponse(
            final String concertName,
            final String imageUrl,
            final String title,
            final String artistName,
            final Region region,
            final String boardingArea,
            final String dropOffArea,
            final String upTime,
            final String downTime,
            final List<LocalDate> rentBoardingDates,
            final BusSize busSize,
            final BusType busType,
            final int maxPassenger,
            final int roundPrice,
            final int upTimePrice,
            final int downTimePrice,
            final int recruitmentCount,
            final LocalDate endDate,
            final String chatUrl,
            final RefundType refundType,
            final String information
    ) {
        this.concertName = concertName;
        this.imageUrl = imageUrl;
        this.title = title;
        this.artistName = artistName;
        this.region = region;
        this.boardingArea = boardingArea;
        this.dropOffArea = dropOffArea;
        this.upTime = upTime;
        this.downTime = downTime;
        this.rentBoardingDates = rentBoardingDates.stream()
                .map(DataConverter::convertToDateWithDayFromLocalDate)
                .toList();
        this.busSize = busSize;
        this.busType = busType;
        this.maxPassenger = maxPassenger;
        this.roundPrice = roundPrice;
        this.upTimePrice = upTimePrice;
        this.downTimePrice = downTimePrice;
        this.recruitmentCount = recruitmentCount;
        this.endDate = endDate;
        this.chatUrl = chatUrl;
        this.refundType = refundType;
        this.information = information;
    }
}
