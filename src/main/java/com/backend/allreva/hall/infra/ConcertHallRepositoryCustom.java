package com.backend.allreva.hall.infra;

import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;

import java.util.List;

public interface ConcertHallRepositoryCustom {
    List<ConcertHallDetail> findDetailByFacilityCode(String hallCode);
}
