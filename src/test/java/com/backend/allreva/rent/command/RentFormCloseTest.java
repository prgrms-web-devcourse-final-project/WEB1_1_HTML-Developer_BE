package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentFormFixture.createRentFormFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.RentFormReadService;
import com.backend.allreva.rent.command.application.RentFormWriteService;
import com.backend.allreva.rent.command.application.dto.RentFormIdRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentFormCloseTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentFormReadService rentFormReadService;
    @Mock
    private RentFormWriteService rentFormWriteService;

    @Test
    void 차량_대절_폼_마감을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentFormIdRequest = new RentFormIdRequest(1L);
        given(rentFormReadService.getRentFormById(anyLong())).willReturn(createRentFormFixture(1L, 1L));
        given(rentFormWriteService.saveRentForm(any(RentForm.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.closeRentForm(rentFormIdRequest, user);

        // then
        var capturedRentForm = getArgumentCaptorValue();
        assertSoftly(softly -> {
            softly.assertThat(capturedRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(capturedRentForm.isClosed()).isTrue();
        });
    }

    private Member createMockUser(final Long userId) {
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
