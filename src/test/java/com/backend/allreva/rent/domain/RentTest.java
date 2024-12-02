package com.backend.allreva.rent.domain;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.rent.exception.RentAccessDeniedException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RentTest {

    @Test
    void 차량_대절_폼_작성자가_아니면_예외를_발생시킨다() {
        // given
        var writerId = 1L;
        var rent = createRentFixture(2L, 1L);

        // when & then
        assertThrows(RentAccessDeniedException.class, () -> rent.validateMine(writerId));
    }

    @Test
    void 차량_대절_폼을_마감한다() {
        // given
        var rent = createRentFixture(1L, 1L);

        // when
        rent.close();

        // then
        assertThat(rent.isClosed()).isTrue();
    }
}
