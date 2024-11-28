package com.backend.allreva.rent.command;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.RentFormWriteService;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest;
import com.backend.allreva.rent.command.application.dto.RentFormRegisterRequest.RentBoardingDateRegisterRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import java.time.LocalDate;
import java.util.List;
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
class RentFormRegisterTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentFormWriteService rentFormWriteService;

    @Test
    void 차량_대절_폼_개설을_성공한다() {
        // given
        var user = createMockUser(1l);
        var rentFormRequest = createRentFormRegisterRequestFixture();
        given(rentFormWriteService.saveRentForm(any(RentForm.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.registerRentForm(rentFormRequest, user);

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

    private RentForm getArgumentCaptorValue() {
        var rentFormCaptor = ArgumentCaptor.forClass(RentForm.class);
        verify(rentFormWriteService).saveRentForm(rentFormCaptor.capture());
        return rentFormCaptor.getValue();
    }

    public RentFormRegisterRequest createRentFormRegisterRequestFixture() {
        return new RentFormRegisterRequest(
                1L,
                "imageUrl",
                "title",
                "artistName",
                Region.서울,
                "depositAccount",
                "boardingArea",
                "09:00",
                "23:00",
                List.of(
                        new RentBoardingDateRegisterRequest(LocalDate.of(2024, 9, 20)),
                        new RentBoardingDateRegisterRequest(LocalDate.of(2024, 9, 21)),
                        new RentBoardingDateRegisterRequest(LocalDate.of(2024, 9, 22))
                ),
                BusSize.LARGE,
                BusType.STANDARD,
                28,
                30000,
                30000,
                30000,
                30,
                LocalDate.of(2024, 9, 13),
                "charUrl",
                RefundType.BOTH,
                "information"
        );
    }
}
