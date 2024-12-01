package com.backend.allreva.rent.query;

import static com.backend.allreva.concert.fixture.ConcertFixture.createConcertFixture;
import static com.backend.allreva.concert.fixture.ConcertHallFixture.createConcertHallFixture;
import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class RentFormDetailReadTest extends IntegrationTestSupport {

    @Autowired
    private RentFormQueryService rentFormQueryService;
    @Autowired
    private RentFormRepository rentFormRepository;
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
        var rentForm = rentFormRepository.save(createRentFormFixture(1L, concert.getId()));

        // when
        var rentFormDetail = rentFormQueryService.getRentFormDetailById(rentForm.getId());

        // then
        assertThat(rentFormDetail).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(rentFormDetail.title()).isEqualTo(rentForm.getDetailInfo().getTitle());
            softly.assertThat(rentFormDetail.concertName()).isEqualTo(concert.getConcertInfo().getTitle());
            softly.assertThat(rentFormDetail.dropOffArea()).isEqualTo(concertHall.getName());
        });
    }

    @Test
    @Transactional
    void 입금_계좌_조회를_성공한다() {
        // given
        var concertHall = concertHallRepository.save(createConcertHallFixture());
        var concert = concertRepository.save(createConcertFixture(concertHall.getId()));
        var rentForm = rentFormRepository.save(createRentFormFixture(1L, concert.getId()));

        // when
        var depositAccount = rentFormQueryService.getDepositAccountById(rentForm.getId());

        // then
        assertThat(depositAccount).isNotNull();
        assertThat(depositAccount.depositAccount()).isEqualTo("depositAccount");
    }
}
