package com.backend.allreva.rent.infra;

import static com.backend.allreva.rent.fixture.RentJoinFixture.createRentJoinFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.rent_join.command.domain.RentJoinRepository;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentJoinWriteTest extends IntegrationTestSupport {

    @Autowired
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_신청_폼을_성공적으로_저장한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoin = createRentJoinFixture(rentId, memberId);

        // when
        var savedRentJoin = rentJoinRepository.save(rentJoin);

        // then
        var expectRentJoin = rentJoinRepository.findById(savedRentJoin.getId()).orElse(null);
        assertThat(expectRentJoin).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(expectRentJoin.getMemberId()).isEqualTo(memberId);
            softly.assertThat(expectRentJoin.getRentId()).isEqualTo(rentId);
        });
    }

    @Test
    void 차량_대절_신청_폼을_성공적으로_삭제한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoin = createRentJoinFixture(rentId, memberId);
        var savedRentJoin = rentJoinRepository.save(rentJoin);

        // when
        rentJoinRepository.delete(savedRentJoin);

        // then
        var deletedRentJoin = rentJoinRepository.findById(savedRentJoin.getId()).orElse(null);
        assertThat(deletedRentJoin).isNull();
    }
}
