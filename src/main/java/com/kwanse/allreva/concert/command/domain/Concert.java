package com.kwanse.allreva.concert.command.domain;

import com.kwanse.allreva.common.application.BaseEntity;
import com.kwanse.allreva.concert.command.domain.value.ConcertInfo;
import com.kwanse.allreva.concert.command.domain.value.IntroduceImage;
import com.kwanse.allreva.concert.command.domain.value.Seller;
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
    private List<Seller> seller;
}
