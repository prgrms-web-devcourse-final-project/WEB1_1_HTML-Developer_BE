package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.rent.command.application.RentReadService;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.exception.RentNotFoundException;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class RentReadTest extends IntegrationTestSupport {

    @Autowired
    private RentReadService rentReadService;
    @Autowired
    private RentRepository rentRepository;

    @AfterEach
    void cleanUp() {
        rentRepository.deleteAll();
    }

    @Test
    @Transactional
    void 차량_대절_폼_조회를_성공한다() {
        // given
        Rent savedRent = rentRepository.save(createRentFixture(1L, 1L));
        rentRepository.flush();

        // when
        var rentForm = rentReadService.getRentById(savedRent.getId());

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
        Rent savedRent = rentRepository.save(createRentFixture(1L, 1L));
        rentRepository.flush();

        // when & then
        assertThrows(RentNotFoundException.class,
                () -> rentReadService.getRentById(savedRent.getId() + 1L));
    }
}
