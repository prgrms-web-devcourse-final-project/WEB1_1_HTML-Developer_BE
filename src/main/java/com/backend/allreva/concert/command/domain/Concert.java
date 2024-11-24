package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.command.domain.value.Seller;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Concert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long viewCount;

    @Embedded
    private Code code;

    @Embedded
    private ConcertInfo concertInfo;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "poster"))
    private Image poster;

    @ElementCollection
    @CollectionTable(
            name = "concert_image",
            joinColumns = @JoinColumn(name = "id")
    )
    private List<Image> detailImages;

    @ElementCollection
    @CollectionTable(
            name = "concert_seller",
            joinColumns = @JoinColumn(name = "id")
    )
    private List<Seller> sellers;

    public void updateFrom(KopisConcertResponse response) {
        this.concertInfo = KopisConcertResponse.toConcertInfo(response);
        this.code = Code.builder()
                .hallCode(response.getHallcd())
                .concertCode(response.getConcertcd())
                .build();
        this.detailImages = KopisConcertResponse.toDetailImages(response.getStyurls());
        this.sellers = KopisConcertResponse.toSellers(response.getRelates());
        this.poster = KopisConcertResponse.toIntroduceImage(response.getPoster());
    }


    @Builder
    public Concert(
            Code code,
            ConcertInfo concertInfo,
            Image poster,
            List<Image> detailImages,
            List<Seller> sellers
    ) {
        this.code = code;
        this.concertInfo = concertInfo;
        this.poster = poster;
        this.detailImages = detailImages;
        this.sellers = sellers;

        this.viewCount = 0L;
    }
}
