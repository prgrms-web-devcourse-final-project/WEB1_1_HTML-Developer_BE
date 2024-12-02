package com.backend.allreva.rent.query;

import static com.backend.allreva.concert.fixture.ConcertFixture.createConcertFixture;
import static com.backend.allreva.concert.fixture.ConcertHallFixture.createConcertHallFixture;
import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.rent.infra.RentJpaRepository;
import com.backend.allreva.rent.query.application.RentQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class RentDetailReadTest extends IntegrationTestSupport {

    @Autowired
    private RentQueryService rentQueryService;
    @Autowired
    private RentJpaRepository rentJpaRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertHallRepository concertHallRepository;

    @Test
    @Transactional
    void 차량_대절_폼_상세_조회를_성공한다() {
        // given
        var concertHall = concertHallRepository.save(createConcertHallFixture());
        var concert = concertRepository.save(createConcertFixture(concertHall.getId()));
        var rent = rentJpaRepository.save(createRentFixture(1L, concert.getId()));

        // when
        var rentDetail = rentQueryService.getRentDetailById(rent.getId());

        // then
        assertThat(rentDetail).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(rentDetail.title()).isEqualTo(rent.getDetailInfo().getTitle());
            softly.assertThat(rentDetail.concertName()).isEqualTo(concert.getConcertInfo().getTitle());
            softly.assertThat(rentDetail.dropOffArea()).isEqualTo(concertHall.getName());
        });
    }

    @Test
    @Transactional
    void 입금_계좌_조회를_성공한다() {
        // given
        var concertHall = concertHallRepository.save(createConcertHallFixture());
        var concert = concertRepository.save(createConcertFixture(concertHall.getId()));
        var rent = rentJpaRepository.save(createRentFixture(1L, concert.getId()));

        // when
        var depositAccount = rentQueryService.getDepositAccountById(rent.getId());

        // then
        assertThat(depositAccount).isNotNull();
        assertThat(depositAccount.depositAccount()).isEqualTo("depositAccount");
    }
}
