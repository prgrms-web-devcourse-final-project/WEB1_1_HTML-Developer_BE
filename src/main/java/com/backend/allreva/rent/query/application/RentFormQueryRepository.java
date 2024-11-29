package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentFormSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentFormQueryRepository {
    Optional<RentFormDetailResponse> findRentFormDetailById(Long rentFormId);
    Optional<DepositAccountResponse> findDepositAccountById(Long rentFormId);
    List<RentFormSummaryResponse> findRentFormSummaries(Region region, SortType sortType, LocalDate lastEndDate, Long lastId,  int pageSize);
}
