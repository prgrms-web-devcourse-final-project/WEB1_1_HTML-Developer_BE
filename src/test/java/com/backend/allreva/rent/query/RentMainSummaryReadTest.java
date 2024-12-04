package com.backend.allreva.rent.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentBoardingDate;
import com.backend.allreva.rent.command.domain.value.AdditionalInfo;
import com.backend.allreva.rent.command.domain.value.Bus;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent.command.domain.value.DetailInfo;
import com.backend.allreva.rent.command.domain.value.OperationInfo;
import com.backend.allreva.rent.command.domain.value.Price;
import com.backend.allreva.rent.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.infra.RentJpaRepository;
import com.backend.allreva.rent.query.application.RentQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import com.backend.allreva.survey.query.application.dto.SortType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
class RentMainSummaryReadTest extends IntegrationTestSupport {

    @Autowired
    private RentQueryService rentQueryService;
    @Autowired
    private RentJpaRepository rentJpaRepository;

    @Test
    void 차량_대절_리스트를_지역별로_조회한다() {
        // given
        var registerId = 1L;
        var savedRent1 = rentJpaRepository.save(createRentFixture(
                registerId, 1L, Region.서울, LocalDate.of(2024, 9, 20)));
        rentJpaRepository.save(createRentFixture(
                registerId, 2L, Region.경기, LocalDate.of(2024, 9, 21)));

        // when
        var rentSummaries = rentQueryService.getRentSummaries(
                Region.서울, SortType.LATEST, null, null, 10);

        // then
        assertThat(rentSummaries).hasSize(1);
        assertThat(rentSummaries.get(0).rentId()).isEqualTo(savedRent1.getId());
    }

    @Test
    void 차량_대절_리스트를_마감순으로_조회한다() {
        // given
        var registerId = 1L;
        var savedRent1 = rentJpaRepository.save(createRentFixture(
                registerId, 1L, Region.서울, LocalDate.of(2024, 9, 21)));
        var savedRent2 = rentJpaRepository.save(createRentFixture(
                registerId, 2L, Region.경기, LocalDate.of(2024, 9, 20)));

        // when
        var rentSummaries = rentQueryService.getRentSummaries(
                null, SortType.CLOSING, null, null, 10);

        // then
        assertThat(rentSummaries).hasSize(2);
        assertThat(rentSummaries.get(0).rentId()).isEqualTo(savedRent2.getId());
        assertThat(rentSummaries.get(1).rentId()).isEqualTo(savedRent1.getId());
    }

    private Rent createRentFixture(
            final Long memberId,
            final Long concertId,
            final Region region,
            final LocalDate endDate
    ) {
        Rent rent = Rent.builder()
                .memberId(memberId)
                .concertId(concertId)
                .detailInfo(DetailInfo.builder()
                        .image(new Image("imageUrl"))
                        .title("title")
                        .artistName("artistName")
                        .depositAccount("depositAccount")
                        .region(region)
                        .build())
                .operationInfo(OperationInfo.builder()
                        .boardingArea("boardingArea")
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
                        .endDate(endDate)
                        .build())
                .build();
        rent.assignBoardingDates(List.of(
                RentBoardingDate.builder()
                        .rent(rent)
                        .date(LocalDate.of(2024, 9, 20))
                        .build(),
                RentBoardingDate.builder()
                        .rent(rent)
                        .date(LocalDate.of(2024, 9, 21))
                        .build()));
        return rent;
    }
}
