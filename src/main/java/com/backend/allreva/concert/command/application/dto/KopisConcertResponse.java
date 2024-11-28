package com.backend.allreva.concert.command.application.dto;

import com.backend.allreva.common.converter.DataConverter;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.value.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KopisConcertResponse {
    private String concertcd; //공연 code
    private String fcltycd; //공연시설 kopis code
    private String prfnm; //공연명
    private String prfpdfrom; //시작 날짜
    private String prfpdto; //종료 날짜
    private String hallcd; //공연장소 kopis code
    private String poster; //포스터
    private String pcseguidance; //가격
    private String prfstate; //공연상태
    private String dtguidance; //공연 타임테이블
    private String entrpsnmH; //주최
    private Set<String> styurls; //소개이미지 list
    private Set<Seller> sellers; //판매처 list

    public static Concert toEntity(final KopisConcertResponse response) {
        return Concert.builder()
                .concertInfo(toConcertInfo(response))
                .poster(toIntroduceImage(response.poster))
                .detailImages(toDetailImages(response.styurls))
                .sellers(response.sellers)
                .code(Code.builder()
                        .concertCode(response.concertcd)
                        .hallCode(response.getHallcd())
                        .build()
                )
                .build();
    }

    public static ConcertInfo toConcertInfo(final KopisConcertResponse response) {
        return ConcertInfo.builder()
                .title(response.prfnm)
                .price(response.pcseguidance)
                .dateInfo(
                        new DateInfo(
                                DataConverter.convertToLocalDate(response.prfpdfrom),
                                DataConverter.convertToLocalDate(response.prfpdto),
                                response.getDtguidance()
                        )
                )
                .prfstate(ConcertStatus.convertToConcertStatus(response.prfstate))
                .host(response.entrpsnmH)
                .build();
    }

    public static Image toIntroduceImage(final String image) {
        return new Image(image);
    }

    public static Set<Image> toDetailImages(final Set<String> styurls) {
        return styurls.stream().map(KopisConcertResponse::toIntroduceImage).collect(Collectors.toSet());
    }

}
