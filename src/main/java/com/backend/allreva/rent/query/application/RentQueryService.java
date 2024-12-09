package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.exception.RentNotFoundException;
import com.backend.allreva.rent.query.application.response.*;
import com.backend.allreva.survey.query.application.response.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        return rentRepository.findRentAdminSummariesByMemberId(memberId);
    }

    public RentAdminDetailResponse getRentAdminDetail(
            final Long memberId,
            final LocalDate boardingDate,
            final Long rentId
    ) {
        RentAdminDetailResponse rentAdminDetailResponse = rentRepository.findRentAdminDetail(memberId,
                        boardingDate, rentId)
                .orElseThrow(RentNotFoundException::new);
        List<RentAdminJoinDetailResponse> rentAdminJoinDetails = rentRepository.findRentAdminJoinDetails(memberId,
                rentId, boardingDate);
        rentAdminDetailResponse.setRentJoinDetailResponses(rentAdminJoinDetails);
        return rentAdminDetailResponse;
    }

}
