package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static com.backend.allreva.rent.fixture.RentFormUpdateRequestFixture.createRentFormUpdateRequestFixture;
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
import com.backend.allreva.rent.command.application.RentFormReadService;
import com.backend.allreva.rent.command.application.RentFormWriteService;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.exception.RentFormAccessDeniedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentFormUpdateTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentFormReadService rentFormReadService;
    @Mock
    private RentFormWriteService rentFormWriteService;

    @Test
    void 차량_대절_폼_수정을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentFormRequest = createRentFormUpdateRequestFixture(1L);
        given(rentFormReadService.getRentFormById(anyLong())).willReturn(createRentFormFixture(1L, 1L));
        given(rentFormWriteService.saveRentForm(any(RentForm.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.updateRentForm(rentFormRequest, user);

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
        var rentFormUpdateRequest = createRentFormUpdateRequestFixture(1L);
        given(rentFormReadService.getRentFormById(anyLong())).willReturn(createRentFormFixture(1L, 1L));

        // when & then
        assertThrows(RentFormAccessDeniedException.class,
                () -> rentCommandService.updateRentForm(rentFormUpdateRequest, user));
        verify(rentFormReadService, times(1)).getRentFormById(anyLong());
    }

    private Member createMockUser(Long userId) {
        var user = Mockito.mock(Member.class);
        when(user.getId()).thenReturn(userId);
        return user;
    }

    private RentForm getArgumentCaptorValue() {
        var rentFormCaptor = ArgumentCaptor.forClass(RentForm.class);
        verify(rentFormWriteService).saveRentForm(rentFormCaptor.capture());
        return rentFormCaptor.getValue();
    }
}
