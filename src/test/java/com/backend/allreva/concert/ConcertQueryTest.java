package com.backend.allreva.concert;


import com.backend.allreva.common.model.Image;
import com.backend.allreva.concert.command.domain.Concert;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.value.*;
import com.backend.allreva.concert.infra.ConcertJpaRepository;
import com.backend.allreva.concert.query.application.ConcertQueryService;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import com.backend.allreva.hall.command.domain.ConcertHall;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.hall.command.domain.value.ConvenienceInfo;
import com.backend.allreva.hall.command.domain.value.Location;
import com.backend.allreva.support.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;

class ConcertQueryTest extends IntegrationTestSupport {

    @Autowired
    private ConcertQueryService concertQueryService;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertHallRepository concertHallRepository;

    private Concert savedConcert;
    private ConcertHall savedConcertHall;


    @BeforeEach
    void setUp() {
        // ConcertHall 저장
        savedConcertHall = ConcertHall.builder()
                .id("hallCode")
                .name("hallName")
                .seatScale(2000)
                .convenienceInfo(
                        new ConvenienceInfo(
                                true, true, true,
                                true, true, true,
                                true, true
                        ))
                .location(new Location(0.0, 1.2, "address"))
                .build();
        concertHallRepository.save(savedConcertHall);

        // Concert 저장
        var dateInfo = DateInfo.builder()
                .stdate(LocalDate.now())
                .eddate(LocalDate.now().plusDays(1))
                .timeTable("timeTable")
                .build();

        var concertInfo = ConcertInfo.builder()
                .title("title")
                .price("price")
                .host("host")
                .prfstate(ConcertStatus.IN_PROGRESS)
                .dateInfo(dateInfo)
                .build();

        var sellers = Set.of(
                Seller.builder()
                        .relateName("relateName")
                        .relateUrl("relateUrl")
                        .build(),
                Seller.builder()
                        .relateName("relateName22")
                        .relateUrl("relateUrl22")
                        .build(),
                Seller.builder()
                        .relateName("relateName33")
                        .relateUrl("relateUrl33")
                        .build()
        );

        savedConcert = Concert.builder()
                .code(new Code("concertCode", "hallCode"))
                .concertInfo(concertInfo)
                .sellers(sellers)
                .poster(new Image("posterUrl"))
                .detailImages(Set.of(new Image("detailImageUrl"), new Image("detailImageUrl22")))
                .build();
        concertRepository.save(savedConcert);

    }

    @AfterEach
    void tearDown() {
        concertRepository.deleteAllInBatch();
        concertHallRepository.deleteAll();
    }

    @DisplayName("공연을 조회하면 공연 상세 정보와 해당 공연장 정보가 반환된다")
    @Test
    void concertDetailTest() {
        // Given
        /**
         * setUp -> Concert & ConcertHall
         */

        // When
        var response = concertQueryService.findDetailById(savedConcert.getId());

        // Then
        Assertions.assertThat(response)
                .extracting(ConcertDetailResponse::getHallCode)
                .isEqualTo(savedConcertHall.getId()); // 공연장 id = hallCode

        Assertions.assertThat(response.getSellers())
                .extracting("relateName")
                .contains("relateName22")
                .contains("relateName"); // 공연장 id = hallCode
    }

    @DisplayName("조회하면 지정된 시간 이후에 조회수가 증가한다")
    @Test
    void detailViewCountTest() throws InterruptedException {

        // Given
        /**
         * setUp -> Concert & ConcertHall
         */

        // When
        concertQueryService.findDetailById(savedConcert.getId());
        concertQueryService.findDetailById(savedConcert.getId());
        Thread.sleep(300);

        Concert concert = concertJpaRepository.findById(savedConcert.getId())
                .get();

        // Then
        Assertions.assertThat(concert.getViewCount()).isEqualTo(2);
    }

    @DisplayName("조회하여도 그 즉시 조회수가 반영되지 않는다")
    @Test
    void scheduleViewCountTest() {

        // Given
        /**
         * setUp -> Concert & ConcertHall
         */

        // When
        concertQueryService.findDetailById(savedConcert.getId());
        Concert concert = concertJpaRepository.findById(savedConcert.getId())
                .get();

        // Then
        Assertions.assertThat(concert.getViewCount()).isZero();
    }
}
