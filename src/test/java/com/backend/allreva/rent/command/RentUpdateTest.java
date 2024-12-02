package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static com.backend.allreva.rent.fixture.RentUpdateRequestFixture.createRentUpdateRequestFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.RentReadService;
import com.backend.allreva.rent.command.application.RentWriteService;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.exception.RentAccessDeniedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentUpdateTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentReadService rentReadService;
    @Mock
    private RentWriteService rentWriteService;

    @Test
    void 차량_대절_폼_수정을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentFormRequest = createRentUpdateRequestFixture(1L);
        given(rentReadService.getRentById(anyLong())).willReturn(createRentFixture(1L, 1L));
        given(rentWriteService.saveRent(any(Rent.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.updateRent(rentFormRequest, user);

        // then
        var capturedRentForm = getArgumentCaptorValue();
        assertSoftly(softly -> {
            softly.assertThat(capturedRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(capturedRentForm.getAdditionalInfo().getInformation()).isEqualTo(rentFormRequest.information());
        });
    }

    @Test
    void 차량_대절_폼이_작성자_본인이_아니라면_예외를_발생시킨다() {
        // given
        var user = createMockUser(2L);
        var rentFormUpdateRequest = createRentUpdateRequestFixture(1L);
        given(rentReadService.getRentById(anyLong())).willReturn(createRentFixture(1L, 1L));

        // when & then
        assertThrows(RentAccessDeniedException.class,
                () -> rentCommandService.updateRent(rentFormUpdateRequest, user));
        verify(rentReadService, times(1)).getRentById(anyLong());
    }

    private Member createMockUser(Long userId) {
        var user = Mockito.mock(Member.class);
        when(user.getId()).thenReturn(userId);
        return user;
    }

    private Rent getArgumentCaptorValue() {
        var rentFormCaptor = ArgumentCaptor.forClass(Rent.class);
        verify(rentWriteService).saveRent(rentFormCaptor.capture());
        return rentFormCaptor.getValue();
    }
}
