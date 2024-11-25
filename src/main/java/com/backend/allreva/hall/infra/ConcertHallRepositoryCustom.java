package com.backend.allreva.hall.infra;

import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;

public interface ConcertHallRepositoryCustom {
    ConcertHallDetail findDetailByHallCode(String hallCode);
}
