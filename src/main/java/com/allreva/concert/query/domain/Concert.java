package com.allreva.concert.query.domain;

import com.allreva.concert.query.domain.value.ConcertInfo;
import com.allreva.concert.query.domain.value.ConcertStatus;
import com.allreva.concert.query.domain.value.IntroduceImage;
import com.allreva.concert.query.domain.value.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hallId;

    private String concertcd;

    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;

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
            name = "concert_seller",
            joinColumns = @JoinColumn(name = "concert_id")
    )
    private List<Seller> seller;

}
