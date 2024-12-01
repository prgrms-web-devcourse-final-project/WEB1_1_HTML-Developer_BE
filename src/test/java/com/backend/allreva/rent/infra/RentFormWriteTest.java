package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormBoardingDateFixture;
import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent.command.application.RentFormWriteService;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentFormWriteTest extends IntegrationTestSupport {

    @Autowired
    private RentFormWriteService rentFormWriteService;
    @Autowired
    private RentFormRepository rentFormRepository;

    @Test
    void 차량_대절_폼을_성공적으로_저장한다() {
        // given
        var rentForm = createRentFormFixture(1L, 1L);

        // when
        var savedRentForm = rentFormWriteService.saveRentForm(rentForm);
        rentFormRepository.flush();

        // then
        var expectRentForm = rentFormRepository.findById(savedRentForm.getId()).orElse(null);
        assertThat(expectRentForm).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(expectRentForm.getMemberId()).isEqualTo(1L);
            softly.assertThat(expectRentForm.getConcertId()).isEqualTo(1L);
            softly.assertThat(expectRentForm.getDetailInfo().getTitle()).isEqualTo("title");
            softly.assertThat(expectRentForm.getDetailInfo().getArtistName()).isEqualTo("artistName");
        });
    }

    @Test
    void 차량_대절_폼을_성공적으로_삭제한다() {
        // given
        var rentForm = createRentFormFixture(1L, 1L);
        RentForm savedRentForm = rentFormRepository.save(rentForm);
        rentFormRepository.flush();

        // when
        rentFormWriteService.deleteRentForm(rentForm);
        rentFormRepository.flush();

        // then
        var deletedRentForm = rentFormRepository.findById(savedRentForm.getId()).orElse(null);
        assertThat(deletedRentForm).isNull();
    }

    @Test
    void 가용_날짜를_성공적으로_수정한다() {
        // given
        var rentForm = createRentFormFixture(1L, 1L);
        RentForm savedRentForm = rentFormRepository.save(rentForm);
        var boardingDates = createRentFormBoardingDateFixture(savedRentForm);

        // when
        rentFormWriteService.updateRentFormBoardingDates(savedRentForm.getId(), boardingDates);

        // then
        var updatedRentForm = rentFormRepository.findById(savedRentForm.getId()).orElse(null);
        assertThat(updatedRentForm).isNotNull();
        assertThat(updatedRentForm.getBoardingDates().get(0).getDate()).isEqualTo(boardingDates.get(0).getDate());
    }
}
