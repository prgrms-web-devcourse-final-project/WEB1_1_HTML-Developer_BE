package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentNotFoundException;
import com.backend.allreva.rent.query.application.response.DepositAccountResponse;
import com.backend.allreva.rent.query.application.response.RentAdminDetailResponse;
import com.backend.allreva.rent.query.application.response.RentAdminSummaryResponse;
import com.backend.allreva.rent.query.application.response.RentDetailResponse;
import com.backend.allreva.rent.query.application.response.RentSummaryResponse;
import com.backend.allreva.survey.query.application.response.SortType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentQueryService {

    private final RentRepository rentRepository;

    public List<RentSummaryResponse> getRentSummaries(
            final Region region,
            final SortType sortType,
            final LocalDate lastEndDate,
            final Long lastId,
            final int pageSize
    ) {
        return rentRepository.findRentSummaries(region, sortType, lastEndDate, lastId, pageSize);
    }

    public List<RentSummaryResponse> getRentMainSummaries(){
        return rentRepository.findRentMainSummaries();
    }

    public RentDetailResponse getRentDetailById(final Long id) {
        return rentRepository.findRentDetailById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    public DepositAccountResponse getDepositAccountById(final Long id) {
        return rentRepository.findDepositAccountById(id)
                .orElseThrow(RentNotFoundException::new);
    }

    public List<RentAdminSummaryResponse> getRentAdminSummariesByMemberId(final Long memberId) {
        return rentRepository.findRentAdminSummaries(memberId);
    }

    public RentAdminDetailResponse getRentAdminDetail(
            final Long memberId,
            final LocalDate boardingDate,
            final Long rentId
    ) {
        return new RentAdminDetailResponse(
                rentRepository.findRentAdminSummary(memberId, boardingDate, rentId)
                        .orElseThrow(RentNotFoundException::new),
                rentRepository.findRentJoinCount(memberId, boardingDate, rentId)
                        .orElseThrow(RentNotFoundException::new),
                rentRepository.findRentJoinDetails(memberId, rentId, boardingDate)
        );
    }
}
