package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentFixture.createRentBoardingDateFixture;
import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent.command.application.RentWriteService;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentWriteTest extends IntegrationTestSupport {

    @Autowired
    private RentWriteService rentWriteService;
    @Autowired
    private RentRepository rentRepository;

    @Test
    void 차량_대절_폼을_성공적으로_저장한다() {
        // given
        var rent = createRentFixture(1L, 1L);

        // when
        var savedRent = rentWriteService.saveRent(rent);
        rentRepository.flush();

        // then
        var expectRent = rentRepository.findById(savedRent.getId()).orElse(null);
        assertThat(expectRent).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(expectRent.getMemberId()).isEqualTo(1L);
            softly.assertThat(expectRent.getConcertId()).isEqualTo(1L);
            softly.assertThat(expectRent.getDetailInfo().getTitle()).isEqualTo("title");
            softly.assertThat(expectRent.getDetailInfo().getArtistName()).isEqualTo("artistName");
        });
    }

    @Test
    void 차량_대절_폼을_성공적으로_삭제한다() {
        // given
        var rent = createRentFixture(1L, 1L);
        Rent savedRent = rentRepository.save(rent);
        rentRepository.flush();

        // when
        rentWriteService.deleteRent(rent);
        rentRepository.flush();

        // then
        var deletedRent = rentRepository.findById(savedRent.getId()).orElse(null);
        assertThat(deletedRent).isNull();
    }

    @Test
    void 가용_날짜를_성공적으로_수정한다() {
        // given
        var rent = createRentFixture(1L, 1L);
        Rent savedRent = rentRepository.save(rent);
        var boardingDates = createRentBoardingDateFixture(savedRent);

        // when
        rentWriteService.updateRentBoardingDates(savedRent.getId(), boardingDates);

        // then
        var updatedRent = rentRepository.findById(savedRent.getId()).orElse(null);
        assertThat(updatedRent).isNotNull();
        assertThat(updatedRent.getBoardingDates().get(0).getDate()).isEqualTo(boardingDates.get(0).getDate());
    }
}
