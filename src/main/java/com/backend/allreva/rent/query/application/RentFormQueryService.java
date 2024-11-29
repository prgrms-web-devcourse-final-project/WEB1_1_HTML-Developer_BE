package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentFormNotFoundException;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentFormSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentFormQueryService {

    private final RentFormQueryRepository rentFormQueryRepository;

    @Transactional(readOnly = true)
    public RentFormDetailResponse getRentFormDetailById(final Long id) {
        return rentFormQueryRepository.findRentFormDetailById(id)
                .orElseThrow(RentFormNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public DepositAccountResponse getDepositAccountById(final Long id) {
        return rentFormQueryRepository.findDepositAccountById(id)
                .orElseThrow(RentFormNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<RentFormSummaryResponse> getRentFormSummaries(
            Region region, SortType sortType, LocalDate lastEndDate, Long lastId,  int pageSize) {
        return rentFormQueryRepository.findRentFormSummaries(region, sortType, lastEndDate, lastId, pageSize);
    }
}
