package com.backend.allreva.rent.command.domain;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.response.*;
import com.backend.allreva.survey.query.application.response.SortType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentRepository {

    Optional<Rent> findById(Long id);

    boolean existsById(Long id);

    Rent save(Rent rent);

    List<RentBoardingDate> updateRentBoardingDates(Long rentId, List<RentBoardingDate> rentBoardingDates);

    void deleteBoardingDateAllByRentId(Long rentId);

    void delete(Rent rent);

    List<RentSummaryResponse> findRentSummaries(Region region, SortType sortType, LocalDate lastEndDate, Long lastId, int pageSize);
    Optional<RentDetailResponse> findRentDetailById(Long rentId);
    Optional<DepositAccountResponse> findDepositAccountById(Long rentId);

    List<RentAdminSummaryResponse> findRentAdminSummariesByMemberId(Long memberId);
    Optional<RentAdminDetailResponse> findRentAdminDetail(Long memberId, LocalDate boardingDate, Long rentId);
    List<RentAdminJoinDetailResponse> findRentAdminJoinDetails(Long memberId, Long rentId, LocalDate boardingDate);

    List<RentSummaryResponse> findRentMainSummaries();
}
