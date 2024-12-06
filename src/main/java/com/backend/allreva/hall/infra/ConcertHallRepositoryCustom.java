package com.backend.allreva.hall.infra;

import com.backend.allreva.hall.query.application.response.ConcertHallDetailResponse;

public interface ConcertHallRepositoryCustom {
    ConcertHallDetailResponse findDetailByHallCode(String hallCode);
}
