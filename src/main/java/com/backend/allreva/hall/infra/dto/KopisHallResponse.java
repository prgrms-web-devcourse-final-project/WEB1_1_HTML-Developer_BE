package com.backend.allreva.hall.infra.dto;

import com.backend.allreva.hall.command.domain.ConcertHall;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class KopisHallResponse {

    @XmlElement(name = "db")
    private Db db;

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Db {
        @XmlElement(name = "fcltynm")
        private String fcltynm; // 공연시설명

        @XmlElement(name = "adres")
        private String adres; // 주소

        @XmlElement(name = "la")
        private String la; // 위도

        @XmlElement(name = "lo")
        private String lo; // 경도

        @XmlElement(name = "restaurant")
        private String restaurant; // 식당 여부

        @XmlElement(name = "cafe")
        private String cafe; // 카페 여부

        @XmlElement(name = "store")
        private String store; // 편의점 여부

        @XmlElement(name = "parkbarrier")
        private String parkbarrier; // 장애인 주차장 여부

        @XmlElement(name = "restbarrier")
        private String restbarrier; // 장애인 화장실 여부

        @XmlElement(name = "runwbarrier")
        private String runwbarrier; // 장애인 경사로 여부

        @XmlElement(name = "elevbarrier")
        private String elevbarrier; // 장애인 엘리베이터 여부

        @XmlElement(name = "parkinglot")
        private String parkinglot; // 주차장 여부

        @XmlElement(name = "mt13s")
        private Mt13s mt13s;
    }

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Mt13s {
        @XmlElement(name = "mt13")
        private List<Mt13> mt13List;
    }

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Mt13 {
        @XmlElement(name = "prfplcnm")
        private String prfplcnm; // 공연장명

        @XmlElement(name = "mt13id")
        private String mt13id; // 공연장 ID

        @XmlElement(name = "seatscale")
        private String seatscale; // 좌석수

    }


    public static ConcertHall toEntity(final KopisHallResponse response, final int idx) {
        Db db = response.getDb();
        return ConcertHall.builder()
                .id(db.mt13s.mt13List.get(idx).mt13id)
                .name(db.fcltynm + " " + db.mt13s.mt13List.get(idx).prfplcnm)
                .seatScale(Integer.parseInt(db.mt13s.mt13List.get(idx).seatscale.replace(",", "")))
                .convenienceInfo(toConvenienceInfo(response))
                .location(toLocation(db.lo, db.la, db.adres))
                .build();
    }

    private static ConvenienceInfo toConvenienceInfo(final KopisHallResponse response) {
        Db db = response.getDb();
        return ConvenienceInfo.builder()
                .store(toBoolean(db.store))
                .cafe(toBoolean(db.cafe))
                .parking(toBoolean(db.parkinglot))
                .restaurant(toBoolean(db.restaurant))
                .parkBarrier(toBoolean(db.parkbarrier))
                .elevBarrier(toBoolean(db.elevbarrier))
                .restBarrier(toBoolean(db.restbarrier))
                .runwBarrier(toBoolean(db.runwbarrier))
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
        return "Y".equals(YN);
    }

}
