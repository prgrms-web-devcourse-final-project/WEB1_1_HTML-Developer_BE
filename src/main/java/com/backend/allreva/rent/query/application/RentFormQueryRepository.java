package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import java.util.Optional;

public interface RentFormQueryRepository {
    Optional<RentFormDetailResponse> findById(Long rentFormId);
}
