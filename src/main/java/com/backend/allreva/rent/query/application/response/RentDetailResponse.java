package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import com.backend.allreva.rent_join.command.domain.value.RefundType;
import com.backend.allreva.rent.command.domain.value.Region;
import java.time.LocalDate;
import java.util.List;

public record RentDetailResponse(
        String concertName, // x -> 대신 concert랑 조인해서 concert name
        String imageUrl,
        String title,
        String artistName,
        Region region,
        String boardingArea, // 상행 지역
        String dropOffArea, // 하행 지역 -> concerthall code 조인
        String upTime,
        String downTime,
        List<LocalDate> rentBoardingDates,
        List<Integer> currentRecruitmentCounts,
        BusSize busSize,
        BusType busType,
        int maxPassenger,
        int roundPrice,
        int upTimePrice,
        int downTimePrice,
        int recruitmentCount,
        LocalDate endDate,
        String chatUrl, // 지금은 필요없을듯 일단은 넣어놓자.
        RefundType refundType,
        String information
) {

}
