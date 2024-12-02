package com.backend.allreva.rent.command.domain;

import java.util.List;

public interface RentBoardingDateRepository {
    List<RentBoardingDate> findByRentId(Long rentId);
}
