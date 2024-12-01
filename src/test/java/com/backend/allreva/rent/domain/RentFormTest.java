package com.backend.allreva.rent.domain;

import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.rent.exception.RentFormAccessDeniedException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RentFormTest {

    @Test
    void 차량_대절_폼_작성자가_아니면_예외를_발생시킨다() {
        // given
        var writerId = 1L;
        var rentForm = createRentFormFixture(2L, 1L);

        // when & then
        assertThrows(RentFormAccessDeniedException.class, () -> rentForm.validateMine(writerId));
    }

    @Test
    void 차량_대절_폼을_마감한다() {
        // given
        var rentForm = createRentFormFixture(1L, 1L);

        // when
        rentForm.close();

        // then
        assertThat(rentForm.isClosed()).isTrue();
    }
}
