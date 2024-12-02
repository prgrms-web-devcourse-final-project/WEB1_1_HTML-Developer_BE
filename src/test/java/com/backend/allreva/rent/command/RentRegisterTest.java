package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentRegisterRequestFixture.createRentRegisterRequestFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.RentWriteService;
import com.backend.allreva.rent.command.domain.Rent;
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
class RentRegisterTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentWriteService rentWriteService;

    @Test
    void 차량_대절_폼_개설을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentFormRequest = createRentRegisterRequestFixture();
        given(rentWriteService.saveRent(any(Rent.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.registerRent(rentFormRequest, user);

        // then
        var capturedRentForm = getArgumentCaptorValue();
        assertSoftly(softly -> {
            softly.assertThat(capturedRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(capturedRentForm.getDetailInfo().getTitle()).isEqualTo(rentFormRequest.title());
        });
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
