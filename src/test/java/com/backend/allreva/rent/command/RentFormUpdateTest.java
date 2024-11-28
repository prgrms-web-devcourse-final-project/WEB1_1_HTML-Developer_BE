package com.backend.allreva.rent.command;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.RentFormReadService;
import com.backend.allreva.rent.command.application.RentFormWriteService;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest;
import com.backend.allreva.rent.command.application.dto.RentFormUpdateRequest.RentBoardingDateUpdateRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.value.AdditionalInfo;
import com.backend.allreva.rent.command.domain.value.Bus;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.DetailInfo;
import com.backend.allreva.rent.command.domain.value.OperationInfo;
import com.backend.allreva.rent.command.domain.value.Price;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentFormAccessDeniedException;
import java.time.LocalDate;
import java.util.List;
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
        var rentFormRequest = createRentFormUpdateRequestFixture();
        given(rentFormReadService.getRentFormById(1L)).willReturn(createRentFormFixture());
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
        var rentFormUpdateRequest = createRentFormUpdateRequestFixture();
        given(rentFormReadService.getRentFormById(anyLong())).willReturn(createRentFormFixture());

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

    public RentFormUpdateRequest createRentFormUpdateRequestFixture() {
        return new RentFormUpdateRequest(
                1L,
                "imageUrl",
                Region.서울,
                "영주",
                "09:00",
                "23:00",
                List.of(
                        new RentBoardingDateUpdateRequest(LocalDate.of(2024, 9, 20)),
                        new RentBoardingDateUpdateRequest(LocalDate.of(2024, 9, 21)),
                        new RentBoardingDateUpdateRequest(LocalDate.of(2024, 9, 22))
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

    // update 시 처음에 RentForm 조회
    private RentForm createRentFormFixture() {
        return RentForm.builder()
                .memberId(1L)
                .concertId(1L)
                .detailInfo(DetailInfo.builder()
                        .image(new Image("imageUrl"))
                        .title("title")
                        .artistName("artistName")
                        .depositAccount("depositAccount")
                        .region(Region.서울)
                        .build())
                .operationInfo(OperationInfo.builder()
                        .boardingArea("boardingArea")
                        .boardingDates(List.of(LocalDate.of(2024, 9, 20)))
                        .upTime("09:00")
                        .downTime("23:00")
                        .bus(Bus.builder()
                                .busSize(BusSize.LARGE)
                                .busType(BusType.STANDARD)
                                .maxPassenger(28)
                                .build())
                        .price(Price.builder()
                                .roundPrice(30000)
                                .upTimePrice(30000)
                                .downTimePrice(30000)
                                .build())
                        .build())
                .additionalInfo(AdditionalInfo.builder()
                        .recruitmentCount(30)
                        .chatUrl("chatUrl")
                        .refundType(RefundType.BOTH)
                        .information("information")
                        .endDate(LocalDate.of(2024, 9, 13))
                        .build())
                .build();
    }
}
