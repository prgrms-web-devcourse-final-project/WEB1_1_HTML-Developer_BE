package com.allreva.concert.command.domain;

import com.allreva.concert.command.domain.value.ConcertInfo;
import com.allreva.concert.command.domain.value.IntroduceImage;
import com.allreva.concert.command.domain.value.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Concert {

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

}
