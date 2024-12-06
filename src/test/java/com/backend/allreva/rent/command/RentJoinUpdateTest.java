package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentJoinFixture.createRentJoinFixture;
import static com.backend.allreva.rent.fixture.RentJoinUpdateRequestFixture.createRentJoinUpdateRequestFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rentJoin.command.domain.RentJoinRepository;
import com.backend.allreva.rentJoin.exception.RentJoinAccessDeniedException;
import com.backend.allreva.rentJoin.exception.RentJoinNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentJoinUpdateTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_신청_폼_수정을_성공한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoinId = 1L;
        var rentJoinRequest = createRentJoinUpdateRequestFixture(rentJoinId);
        var rentJoin = createRentJoinFixture(rentId, memberId);
        given(rentJoinRepository.findById(anyLong())).willReturn(Optional.of(rentJoin));

        // when
        rentCommandService.updateRentJoin(rentJoinRequest, memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(rentJoin.getMemberId()).isEqualTo(memberId);
            softly.assertThat(rentJoin.getRentId()).isEqualTo(rentId);
            softly.assertThat(rentJoin.getDepositor().getDepositorTime()).isEqualTo(rentJoinRequest.depositorTime());
        });
    }

    @Test
    void 차량_대절_신청_폼이_작성자_본인이_아니라면_예외를_발생시킨다() {
        // given
        var memberId = 1L;
        var anotherMemberId = 2L;
        var rentId = 1L;
        var rentJoinId = 1L;
        var rentJoinUpdateRequest = createRentJoinUpdateRequestFixture(rentJoinId);
        given(rentJoinRepository.findById(anyLong())).willReturn(Optional.of(createRentJoinFixture(rentId, memberId)));

        // when & then
        assertThrows(RentJoinAccessDeniedException.class,
                () -> rentCommandService.updateRentJoin(rentJoinUpdateRequest, anotherMemberId));
        verify(rentJoinRepository, times(1)).findById(anyLong());
    }

    @Test
    void 차량_대절_신청_폼이_없을_경우_예외를_발생시킨다() {
        // given
        var memberId = 1L;
        var rentJoinId = 1L;
        var rentJoinUpdateRequest = createRentJoinUpdateRequestFixture(rentJoinId);
        given(rentJoinRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(RentJoinNotFoundException.class,
                () -> rentCommandService.updateRentJoin(rentJoinUpdateRequest, memberId));
    }
}
