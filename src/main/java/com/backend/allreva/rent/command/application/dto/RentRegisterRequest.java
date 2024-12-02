package com.backend.allreva.rent.command.application.dto;

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
import java.time.LocalDate;
import java.util.List;

public record RentRegisterRequest(
        Long concertId,
        String imageUrl,
        String title,
        String artistName,
        Region region, // enum 파싱
        String depositAccount,
        String boardingArea,
        String upTime,
        String downTime,
        List<LocalDate> rentBoardingDateRequests,
        BusSize busSize, // enum
        BusType busType, // enum
        int maxPassenger,
        int roundPrice,
        int upTimePrice,
        int downTimePrice,
        int recruitmentCount,
        LocalDate endDate,
        String chatUrl,
        RefundType refundType, //enum
        String information
) {

    public Rent toEntity(final Long memberId) {
        Rent rent = Rent.builder()
                .memberId(memberId)
                .concertId(concertId)
                .boardingDates(rentBoardingDateRequests.stream()
                        .map(request -> RentBoardingDate.builder()
                                .date(request)
                                .build())
                        .toList())
                .detailInfo(DetailInfo.builder()
                        .image(new Image(imageUrl))
                        .title(title)
                        .artistName(artistName)
                        .depositAccount(depositAccount)
                        .region(region)
                        .build())
                .operationInfo(OperationInfo.builder()
                        .boardingArea(boardingArea)
                        .upTime(upTime)
                        .downTime(downTime)
                        .bus(Bus.builder()
                                .busSize(busSize)
                                .busType(busType)
                                .maxPassenger(maxPassenger)
                                .build())
                        .price(Price.builder()
                                .roundPrice(roundPrice)
                                .upTimePrice(upTimePrice)
                                .downTimePrice(downTimePrice)
                                .build())
                        .build())
                .additionalInfo(AdditionalInfo.builder()
                        .recruitmentCount(recruitmentCount)
                        .chatUrl(chatUrl)
                        .refundType(refundType)
                        .information(information)
                        .endDate(endDate)
                        .build())
                .build();

        List<RentBoardingDate> rentBoardingDates = rentBoardingDateRequests.stream()
                .map(date -> RentBoardingDate.builder()
                        .date(date)
                        .build())
                .toList();
        rent.assignBoardingDates(rentBoardingDates);
        return rent;
    }
}
