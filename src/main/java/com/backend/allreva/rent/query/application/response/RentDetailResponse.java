package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RentDetailResponse {

    private final String concertName; // 콘서트 이름
    private final String imageUrl; // 콘서트 이미지
    private final String title; // 차량 대절 이름
    private final String artistName; // 아티스트 이름
    private final Region region; // 차량 대절 지역
    private final String boardingArea; // 상행 지역
    private final String dropOffArea; // 하행 지역 -> concerthall code 조인
    private final String upTime; // 상행 시간
    private final String downTime; // 하행 시간
    @Setter
    private List<RentBoardingDateResponse> boardingDates;
    private final BusSize busSize; // 차량 정보 - 사이즈
    private final BusType busType; // 차량 정보 - 버스 타입
    private final int maxPassenger; // 차량 정보 - 최대 탑승 인원
    private final int roundPrice; // 왕복 가격
    private final int upTimePrice; // 상행 가격
    private final int downTimePrice; // 하행 가격
    private final int recruitmentCount; // 모집 인원
    private final LocalDate endDate; // 마감일
    private final String chatUrl; // 채팅 url - 지금은 필요없을듯 일단은 넣어놓자.
    private final RefundType refundType; //
    private final String information; // 기타 안내 사항
    private final boolean isClosed; // 마감 여부

    public RentDetailResponse(
            String concertName,
            String imageUrl,
            String title,
            String artistName,
            Region region,
            String boardingArea,
            String dropOffArea,
            String upTime,
            String downTime,
            BusSize busSize,
            BusType busType,
            int maxPassenger,
            int roundPrice,
            int upTimePrice,
            int downTimePrice,
            int recruitmentCount,
            LocalDate endDate,
            String chatUrl,
            RefundType refundType,
            String information,
            boolean isClosed
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
        this.isClosed = isClosed;
    }

    public record RentBoardingDateResponse(
            LocalDate date,
            int participationCount
    ) {

    }
}
