package com.backend.allreva.rent.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.application.RentFormWriteService;
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
import com.backend.allreva.support.IntegrationTestSupport;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class RentFormWriteTest extends IntegrationTestSupport {

    @Autowired
    private RentFormWriteService rentFormWriteService;
    @Autowired
    private RentFormRepository rentFormRepository;

    @Test
    @Transactional
    void 차량_대절_폼을_성공적으로_저장한다() {
        // given
        var rentForm = createRentFormFixture();

        // when
        var savedRentForm = rentFormWriteService.saveRentForm(rentForm);
        rentFormRepository.flush();

        // then
        var expectRentForm = rentFormRepository.findById(savedRentForm.getId()).orElse(null);
        assertThat(expectRentForm).isNotNull();
        assertSoftly(softly -> {
            softly.assertThat(expectRentForm.getMemberId()).isEqualTo(1L);
            softly.assertThat(expectRentForm.getConcertId()).isEqualTo(1L);
            softly.assertThat(expectRentForm.getDetailInfo().getTitle()).isEqualTo("title");
            softly.assertThat(expectRentForm.getDetailInfo().getArtistName()).isEqualTo("artistName");
        });
    }

    @Test
    void 차량_대절_폼을_성공적으로_삭제한다() {
        // given
        var rentForm = createRentFormFixture();
        RentForm savedRentForm = rentFormRepository.save(rentForm);
        rentFormRepository.flush();

        // when
        rentFormWriteService.deleteRentForm(rentForm);
        rentFormRepository.flush();

        // then
        var deletedRentForm = rentFormRepository.findById(savedRentForm.getId()).orElse(null);
        assertThat(deletedRentForm).isNull();
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
