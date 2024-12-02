package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentNotFoundException;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentQueryService {

    private final RentQueryRepository rentQueryRepository;

    public RentDetailResponse getRentDetailById(final Long id) {
        return rentQueryRepository.findRentDetailById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    public DepositAccountResponse getDepositAccountById(final Long id) {
        return rentQueryRepository.findDepositAccountById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    public List<RentSummaryResponse> getRentSummaries(
            final Region region,
            final SortType sortType,
            final LocalDate lastEndDate,
            final Long lastId,
            final int pageSize) {
        return rentQueryRepository.findRentSummaries(region, sortType, lastEndDate, lastId, pageSize);
    }
}
