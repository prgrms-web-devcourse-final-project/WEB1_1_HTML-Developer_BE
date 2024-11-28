package com.backend.allreva.rent.command;

import static com.backend.allreva.member.fixture.MemberFixture.createMemberFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.backend.allreva.member.command.domain.value.MemberRole;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        var user = createMemberFixture(1L, MemberRole.USER);
        var rentFormRequest = createRentFormRegisterRequestFixture();
        given(rentFormWriteService.saveRentForm(any(RentForm.class))).willReturn(rentFormRequest.toEntity(user.getId()));

        // when
        var registeredRentForm = rentCommandService.registerRentForm(rentFormRequest, user);

        // then
        assertSoftly(softly -> {
            softly.assertThat(registeredRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(registeredRentForm.getDetailInfo().getTitle()).isEqualTo(rentFormRequest.title());
        });
    }

    public RentFormRegisterRequest createRentFormRegisterRequestFixture() {
        return new RentFormRegisterRequest(
                1L,
                "imageUrl",
                "차대절",
                "하현상",
                Region.SEOUL,
                "입금계좌",
                "영주",
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
