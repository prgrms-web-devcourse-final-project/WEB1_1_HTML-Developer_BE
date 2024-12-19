package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import java.time.LocalDate;
import java.util.List;

public record RentDetailResponse(
        String concertName, // 콘서트 이름
        String imageUrl, // 콘서트 이미지
        String title, // 차량 대절 이름
        String artistName, // 아티스트 이름
        Region region, // 차량 대절 지역
        String boardingArea, // 상행 지역
        String dropOffArea, // 하행 지역 -> concerthall code 조인
        String upTime, // 상행 시간
        String downTime, // 하행 시간
        List<RentBoardingDateResponse> boardingDates,
        BusSize busSize, // 차량 정보 - 사이즈
        BusType busType, // 차량 정보 - 버스 타입
        int maxPassenger, // 차량 정보 - 최대 탑승 인원
        int roundPrice, // 왕복 가격
        int upTimePrice, // 상행 가격
        int downTimePrice, // 하행 가격
        int recruitmentCount, // 모집 인원
        LocalDate endDate, // 마감일
        String chatUrl, // 채팅 url - 지금은 필요없을듯 일단은 넣어놓자.
        RefundType refundType, //
        String information, // 기타 안내 사항
        boolean isClosed // 마감 여부
) {

    public record RentBoardingDateResponse(
            LocalDate date,
            int participationCount
    ) {

    }
}
