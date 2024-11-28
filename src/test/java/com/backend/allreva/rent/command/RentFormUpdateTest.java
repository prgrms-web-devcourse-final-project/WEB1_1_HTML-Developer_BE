package com.backend.allreva.rent.command;

import static com.backend.allreva.member.fixture.MemberFixture.createMemberFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.member.command.domain.value.MemberRole;
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
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        var user = createMemberFixture(1L, MemberRole.USER);
        var rentFormRequest = createRentFormUpdateRequestFixture();
        given(rentFormReadService.getRentFormById(1L)).willReturn(createRentFormFixture());
        given(rentFormWriteService.saveRentForm(any(RentForm.class))).willReturn(null);

        // when
        var updatedRentForm = rentCommandService.updateRentForm(rentFormRequest, user);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(updatedRentForm.getAdditionalInfo().getInformation()).isEqualTo(rentFormRequest.information());
        });
    }

    public RentFormUpdateRequest createRentFormUpdateRequestFixture() {
        return new RentFormUpdateRequest(
                1L,
                "imageUrl",
                Region.SEOUL,
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

    private RentForm createRentFormFixture() {
        return RentForm.builder()
                .memberId(1L)
                .concertId(1L)
                .detailInfo(DetailInfo.builder()
                        .image(new Image("imageUrl"))
                        .title("title")
                        .artistName("artistName")
                        .depositAccount("depositAccount")
                        .region(Region.SEOUL)
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
