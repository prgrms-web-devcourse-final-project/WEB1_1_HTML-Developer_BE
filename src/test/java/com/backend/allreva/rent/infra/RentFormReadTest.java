package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.rent.command.application.RentFormReadService;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.exception.RentFormNotFoundException;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class RentFormReadTest extends IntegrationTestSupport {

    @Autowired
    private RentFormReadService rentFormReadService;
    @Autowired
    private RentFormRepository rentFormRepository;

    @AfterEach
    void cleanUp() {
        rentFormRepository.deleteAll();
    }

    @Test
    @Transactional
    void 차량_대절_폼_조회를_성공한다() {
        // given
        RentForm savedRentForm = rentFormRepository.save(createRentFormFixture(1L, 1L));
        rentFormRepository.flush();

        // when
        var rentForm = rentFormReadService.getRentFormById(savedRentForm.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(rentForm.getMemberId()).isEqualTo(1L);
            softly.assertThat(rentForm.getDetailInfo().getTitle()).isEqualTo("title");
        });
    }

    @Test
    @Transactional
    void 차량_대절_폼이_없을_경우_예외를_발생시킨다() {
        // given
        RentForm savedRentForm = rentFormRepository.save(createRentFormFixture(1L, 1L));
        rentFormRepository.flush();

        // when & then
        assertThrows(RentFormNotFoundException.class,
                () -> rentFormReadService.getRentFormById(savedRentForm.getId() + 1L));
    }
}
