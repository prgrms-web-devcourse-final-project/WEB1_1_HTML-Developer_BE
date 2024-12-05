package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentJoinFixture.createRentJoinFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent.command.domain.RentJoinRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentJoinReadTest extends IntegrationTestSupport {

    @Autowired
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_신청_폼을_성공적으로_조회한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var savedRentJoin = rentJoinRepository.save(createRentJoinFixture(rentId, memberId));

        // when
        var rentJoinOptional = rentJoinRepository.findById(savedRentJoin.getId());

        // then
        assertThat(rentJoinOptional).isPresent();
        assertSoftly(softly -> {
            var rentJoin = rentJoinOptional.get();
            softly.assertThat(rentJoin.getMemberId()).isEqualTo(1L);
            softly.assertThat(rentJoin.getRentId()).isEqualTo(1L);
        });
    }

    @Test
    void 차량_대절_신청_폼이_존재하는지_확인한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoin = createRentJoinFixture(rentId, memberId);
        rentJoinRepository.save(rentJoin);

        // when
        var exists = rentJoinRepository.existsById(rentJoin.getId());

        // then
        assertThat(exists).isTrue();
    }
}
