package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.IntroduceImage;
import com.backend.allreva.concert.command.domain.value.Seller;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Concert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concert_view_count")
    private Long viewCount;

    @Embedded
    private Code code;

    @Embedded
    private ConcertInfo concertInfo;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "poster"))
    private IntroduceImage poster;

    @ElementCollection
    @CollectionTable(
            name = "concert_images",
            joinColumns = @JoinColumn(name = "concert_id")
    )
    private List<IntroduceImage> detailImages;


    @ElementCollection
    @CollectionTable(
            name = "concert_sellers",
            joinColumns = @JoinColumn(name = "concert_id")
    )
    private List<Seller> sellers;

    public void updateFrom(KopisConcertResponse response) {
        this.concertInfo = KopisConcertResponse.toConcertInfo(response);
        this.code = Code.builder()
                .hallCode(response.getHallcd())
                .concertCode(response.getConcertcd())
                .facilityCode(response.getFcltycd())
                .build();
        this.detailImages = KopisConcertResponse.toDetailImages(response.getStyurls());
        this.sellers = KopisConcertResponse.toSellers(response.getRelates());
        this.poster = KopisConcertResponse.toIntroduceImage(response.getPoster());
    }

    @Builder
    public Concert(
            Code code,
            ConcertInfo concertInfo,
            IntroduceImage poster,
            List<IntroduceImage> detailImages,
            List<Seller> sellers
    ) {
        this.code = code;
        this.concertInfo = concertInfo;
        this.poster = poster;
        this.detailImages = detailImages;
        this.sellers = sellers;
    }
}
