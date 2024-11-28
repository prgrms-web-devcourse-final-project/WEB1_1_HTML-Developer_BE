package com.backend.allreva.rent.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.allreva.common.model.Image;
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

@SuppressWarnings("NonAsciiCharacters")
class RentFormTest {

    @Test
    void 차량_대절_폼_작성자가_아니면_예외를_발생시킨다() {
        // given
        var writerId = 1L;
        var rentForm = createRentFormFixture(2L);

        // when & then
        assertThrows(RentFormAccessDeniedException.class, () -> rentForm.validateMine(writerId));
    }

    @Test
    void 차량_대절_폼을_마감한다() {
        // given
        var rentForm = createRentFormFixture(1L);

        // when
        rentForm.close();

        // then
        assertThat(rentForm.isClosed()).isTrue();
    }

    private RentForm createRentFormFixture(final Long memberId) {
        return RentForm.builder()
                .memberId(memberId)
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
