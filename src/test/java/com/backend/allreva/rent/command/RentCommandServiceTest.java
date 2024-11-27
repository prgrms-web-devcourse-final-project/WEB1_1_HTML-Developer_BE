package com.backend.allreva.rent.command;

import static com.backend.allreva.member.fixture.MemberFixture.createMemberFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.dto.RentFormRequest;
import com.backend.allreva.rent.command.application.dto.RentFormRequest.RentBoardingDateRequest;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.support.IntegrationTestSupport;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
public class RentCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    private RentCommandService rentCommandService;

    @Test
    void 차량_대절_폼_개설_요청이_들어올_때_폼을_성공적으로_저장한다() {
        // given
        var user = createMemberFixture(1L, MemberRole.USER);
        var rentFormRequest = createRentFormRequestFixture();

        // when
        rentCommandService.registerRentForm(rentFormRequest, user);

        // then
        RentForm registeredRentForm = rentCommandService.getRentFormById(1L);
        assertSoftly(softly -> {
            softly.assertThat(registeredRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(registeredRentForm.getDetailInfo().getTitle()).isEqualTo(rentFormRequest.title());
        });
    }

    public RentFormRequest createRentFormRequestFixture() {
        return new RentFormRequest(
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
                        new RentBoardingDateRequest(LocalDate.of(2024, 9, 20)),
                        new RentBoardingDateRequest(LocalDate.of(2024, 9, 21)),
                        new RentBoardingDateRequest(LocalDate.of(2024, 9, 22))
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
