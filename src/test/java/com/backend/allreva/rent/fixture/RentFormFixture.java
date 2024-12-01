package com.backend.allreva.rent.fixture;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.rent.command.domain.RentForm;
import com.backend.allreva.rent.command.domain.RentFormBoardingDate;
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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentFormFixture {

    public static RentForm createRentFormFixture(final Long memberId, final Long concertId) {
        RentForm rentForm = RentForm.builder()
                .memberId(memberId)
                .concertId(concertId)
                .detailInfo(DetailInfo.builder()
                        .image(new Image("imageUrl"))
                        .title("title")
                        .artistName("artistName")
                        .depositAccount("depositAccount")
                        .region(Region.서울)
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
                        .endDate(LocalDate.of(2024, 9, 13))
                        .build())
                .build();
        rentForm.assignBoardingDates(List.of(RentFormBoardingDate.builder()
                .rentForm(rentForm)
                .date(LocalDate.of(2024, 9, 20))
                .build()));
        return rentForm;
    }

    public static List<RentFormBoardingDate> createRentFormBoardingDateFixture(final RentForm rentForm) {
        return List.of(RentFormBoardingDate.builder()
                .rentForm(rentForm)
                .date(LocalDate.of(2024, 9, 20))
                .build());
    }
}
