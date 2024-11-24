package com.backend.allreva.hall.command.application.dto;

import com.backend.allreva.hall.command.domain.ConcertHall;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KopisHallResponse {
    private String fcltynm; //공연시설명
    private String fcltycd; //공연시설코드
    private String prfplcnm; //공연장명
    private String seatscale; //좌석수
    private String mt13id; //공연장코드
    private String adres; //주소
    private String la; //경도
    private String lo; //위도
    private String restaurant; //식당여부
    private String cafe; //카페여부
    private String store; //편의점 여부
    private String parkinglot; //주차장 여부
    private String parkbarrier; //장애인 주차장 여부
    private String restbarrier; //장애인 화장실 여부
    private String runwbarrier; //장애인 경사로 여부
    private String elevbarrier; //장애인 엘리베이터

    public static ConcertHall toEntity(final KopisHallResponse response) {
        return ConcertHall.builder()
                .id(response.getMt13id())
                .name(response.getFcltynm() + " " +response.getPrfplcnm())
                .seatScale(Integer.parseInt(response.seatscale.replace(",","")))
                .star(0.0)
                .convenienceInfo(toConvenienceInfo(response))
                .location(toLocation(response.getLo(), response.getLa(), response.getAdres()))
                .build();
    }

    private static ConvenienceInfo toConvenienceInfo(final KopisHallResponse response) {
        return ConvenienceInfo.builder()
                .store(toBoolean(response.store))
                .cafe(toBoolean(response.cafe))
                .parking(toBoolean(response.parkinglot))
                .restaurant(toBoolean(response.restaurant))
                .parkBarrier(toBoolean(response.parkbarrier))
                .elevBarrier(toBoolean(response.elevbarrier))
                .restBarrier(toBoolean(response.restbarrier))
                .runwBarrier(toBoolean(response.runwbarrier))
                .build();
    }

    private static Location toLocation(final String lo, final String la, final String adres) {
        return Location.builder()
                .longitude(Double.parseDouble(lo))
                .latitude(Double.parseDouble(la))
                .address(adres)
                .build();
    }

    private static boolean toBoolean(String YN) {
        if(YN.equals("Y")) return true;
        else return false;
    }





}