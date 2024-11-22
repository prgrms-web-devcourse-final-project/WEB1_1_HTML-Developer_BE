package com.backend.allreva.concert.command.domain;

import com.backend.allreva.common.application.BaseEntity;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.command.domain.value.ConcertInfo;
import com.backend.allreva.concert.command.domain.value.Seller;
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

    private String concertCode;

    private String hallCode;

    private String facilityCode;

    @Embedded
    private ConcertInfo concertInfo;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "poster"))
    private Image poster;

    @ElementCollection
    @CollectionTable(
            name = "concert_images",
            joinColumns = @JoinColumn(name = "concert_id")
    )
    private List<Image> detailImages;


    @ElementCollection
    @CollectionTable(
            name = "concert_sellers",
            joinColumns = @JoinColumn(name = "concert_id")
    )
    private List<Seller> sellers;

    public void updateFrom(KopisConcertResponse response) {
        this.concertInfo = KopisConcertResponse.toConcertInfo(response);
        this.concertCode = response.getConcertcd();
        this.hallCode = response.getHallcd();
        this.detailImages = KopisConcertResponse.toDetailImages(response.getStyurls());
        this.sellers = KopisConcertResponse.toSellers(response.getRelates());
        this.poster = KopisConcertResponse.toIntroduceImage(response.getPoster());
    }
}
