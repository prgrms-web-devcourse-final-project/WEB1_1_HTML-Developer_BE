package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.model.BaseEntity;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.value.Code;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.Seller;
import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_hall_code", columnList = "hall_code"))
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

    public void updateFrom(String hallCode, KopisConcertResponse.Db db) {
        this.concertInfo = KopisConcertResponse.toConcertInfo(db);
        this.detailImages = KopisConcertResponse.toDetailImages(db.getStyurls().getStyurl());
        this.sellers = KopisConcertResponse.toSellers(db.getRelates().getRelate());
        this.poster = KopisConcertResponse.toIntroduceImage(db.getPoster());
        this.code = Code.builder()
                .concertCode(db.getConcertCode())
                .hallCode(hallCode)
                .build();
    }


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

    public void addViewCount(final int count) {
        this.viewCount += count;
    }
}
