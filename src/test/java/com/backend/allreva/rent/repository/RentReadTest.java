package com.backend.allreva.rent.repository;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.infra.rdb.RentJpaRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentReadTest extends IntegrationTestSupport {

    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private RentJpaRepository rentJpaRepository;

    @AfterEach
    void cleanUp() {
        rentJpaRepository.deleteAll();
    }

    @Test
    void 차량_대절_폼_조회를_성공한다() {
        // given
        Rent savedRent = rentJpaRepository.save(createRentFixture(1L, 1L));
        rentJpaRepository.flush();

        // when
        var rentOptional = rentRepository.findById(savedRent.getId());

        // then
        assertThat(rentOptional).isPresent();
        assertSoftly(softly -> {
            var rent = rentOptional.get();
            softly.assertThat(rent.getMemberId()).isEqualTo(1L);
            softly.assertThat(rent.getDetailInfo().getTitle()).isEqualTo("title");
        });
    }
}

