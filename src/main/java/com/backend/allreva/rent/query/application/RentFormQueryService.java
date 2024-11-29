package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.exception.RentFormNotFoundException;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentFormDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentFormQueryService {

    private final RentFormQueryRepository rentFormQueryRepository;

    @Transactional(readOnly = true)
    public RentFormDetailResponse getRentFormDetailById(final Long id) {
        return rentFormQueryRepository.findById(id)
                .orElseThrow(RentFormNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public DepositAccountResponse getDepositAccountById(final Long id) {
        return rentFormQueryRepository.findDepositAccountById(id)
                .orElseThrow(RentFormNotFoundException::new);
    }
}
