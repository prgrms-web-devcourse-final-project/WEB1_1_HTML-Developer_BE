package com.backend.allreva.rent.infra;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.application.RentFormReadService;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.command.domain.value.AdditionalInfo;
import com.backend.allreva.rent.command.domain.value.Bus;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.DetailInfo;
import com.backend.allreva.rent.command.domain.value.OperationInfo;
import com.backend.allreva.rent.command.domain.value.Price;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentFormNotFoundException;
import com.backend.allreva.support.IntegrationTestSupport;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class RentFormReadTest extends IntegrationTestSupport {

    @Autowired
    private RentFormReadService rentFormReadService;
    @Autowired
    private RentFormRepository rentFormRepository;

    @BeforeEach
    void setUp() {
        var rentForm = createRentFormFixture();
        rentFormRepository.save(rentForm);
        rentFormRepository.flush();
    }

    @Test
    void 차량_대절_폼_조회를_성공한다() {
        // given
        var rentFormId = 1L;

        // when
        var rentForm = rentFormReadService.getRentFormById(rentFormId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(rentForm.getMemberId()).isEqualTo(1L);
            softly.assertThat(rentForm.getDetailInfo().getTitle()).isEqualTo("title");
        });
    }

    @Test
    void 차량_대절_폼이_없을_경우_예외를_발생시킨다() {
        // given
        var rentFormId = 2L;

        // when & then
        assertThrows(RentFormNotFoundException.class, () -> rentFormReadService.getRentFormById(rentFormId));
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
