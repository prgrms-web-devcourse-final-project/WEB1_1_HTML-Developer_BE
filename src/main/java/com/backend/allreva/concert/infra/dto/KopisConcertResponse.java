package com.backend.allreva.concert.infra.dto;

import com.backend.allreva.common.converter.DataConverter;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.value.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.backend.allreva.concert.infra.dto.KopisConcertResponse.Db.Relate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class KopisConcertResponse {
    @XmlElement(name = "db")
    private Db db;

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Db {
        @XmlElement(name = "mt20id")
        private String concertCode; //공연 code
        @XmlElement(name = "prfnm")
        private String prfnm; //공연명
        @XmlElement(name = "prfpdfrom")
        private String prfpdfrom; //시작 날짜
        @XmlElement(name = "prfpdto")
        private String prfpdto; //종료 날짜
        @XmlElement(name = "poster")
        private String poster; //포스터
        @XmlElement(name = "pcseguidance")
        private String pcseguidance; //가격
        @XmlElement(name = "prfstate")
        private String prfstate; //공연상태
        @XmlElement(name = "dtguidance")
        private String dtguidance; //공연 타임테이블
        @XmlElement(name = "entrpsnmH")
        private String entrpsnmH; //주최
        @XmlElement(name = "styurls")
        private Styurls styurls; //소개이미지 list
        @XmlElement(name = "relates")
        private Relates relates; //판매처 list

        @Getter
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Styurls {
            @XmlElement(name = "styurl")
            private List<String> styurl;
        }

        @Getter
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Relates {
            @XmlElement(name = "relate")
            private List<Relate> relate;
        }

        @Getter
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Relate {
            @XmlElement(name = "relatenm")
            private String relatenm;
            @XmlElement(name = "relateurl")
            private String relateurl;
        }
    }


    public static Concert toEntity(final String hallCode,
                                   final KopisConcertResponse response) {
        Db db = response.getDb();
        return Concert.builder()
                .concertInfo(toConcertInfo(db))
                .poster(toIntroduceImage(db.poster))
                .detailImages(toDetailImages(db.styurls.styurl))
                .sellers(toSellers(db.relates.relate))
                .code(Code.builder().concertCode(db.concertCode).hallCode(hallCode).build())
                .build();
    }

    public static ConcertInfo toConcertInfo(final KopisConcertResponse.Db db) {
        return ConcertInfo.builder()
                .title(db.prfnm)
                .host(db.entrpsnmH)
                .price(db.pcseguidance)
                .prfstate(ConcertStatus.convertToConcertStatus(db.prfstate))
                .dateInfo(
                        new DateInfo(
                                DataConverter.convertToLocalDate(db.prfpdfrom),
                                DataConverter.convertToLocalDate(db.prfpdto),
                                db.getDtguidance()
                        )
                )
                .build();
    }

    public static Seller toSeller(final Relate relate) {
        return Seller.builder()
                .relateName(relate.getRelatenm())
                .relateUrl(relate.getRelateurl())
                .build();
    }

    public static Set<Seller> toSellers(final List<Relate> relates) {
        return relates.stream().map(KopisConcertResponse::toSeller).collect(Collectors.toSet());
    }

    public static Image toIntroduceImage(final String image) {
        return new Image(image);
    }

    public static Set<Image> toDetailImages(final List<String> styurls) {
        return styurls.stream().map(KopisConcertResponse::toIntroduceImage).collect(Collectors.toSet());
    }

}
