package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    private Set<Image> detailImages;

    @ElementCollection
    @CollectionTable(
            name = "concert_seller",
            joinColumns = @JoinColumn(name = "id")
    )
    private Set<Seller> sellers;

    @Builder
    public Concert(
            final Code code,
            final ConcertInfo concertInfo,
            final Image poster,
            final Set<Image> detailImages,
            final Set<Seller> sellers
    ) {
        this.code = code;
        this.concertInfo = concertInfo;
        this.poster = poster;
        this.detailImages = detailImages;
        this.sellers = sellers;

        this.viewCount = 0L;
    }

    public void updateFrom(final KopisConcertResponse response) {
        this.concertInfo = KopisConcertResponse.toConcertInfo(response);
        this.code = Code.builder()
                .hallCode(response.getHallcd())
                .concertCode(response.getConcertcd())
                .build();
        this.detailImages = KopisConcertResponse.toDetailImages(response.getStyurls());
        this.sellers = response.getSellers();
        this.poster = KopisConcertResponse.toIntroduceImage(response.getPoster());
    }

    public void addViewCount(final int count) {
        this.viewCount += count;
    }
}
