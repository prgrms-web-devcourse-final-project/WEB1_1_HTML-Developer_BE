package com.backend.allreva.rent.command.application.request;

import com.backend.allreva.rent.command.domain.RentBoardingDate;
import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rentJoin.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import java.time.LocalDate;
import java.util.List;

public record RentUpdateRequest(
        Long rentId,
        String imageUrl,
        Region region, // enum 파싱
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

    public List<RentBoardingDate> toRentBoardingDates() {
        return rentBoardingDateRequests.stream()
                .map(request -> RentBoardingDate.builder()
                        .date(request)
                        .build())
                .toList();
    }
}
