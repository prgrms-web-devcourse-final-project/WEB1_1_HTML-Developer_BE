package com.backend.allreva.rent.query.application;

import com.backend.allreva.rent.command.domain.value.Region;
import com.backend.allreva.rent.query.application.dto.DepositAccountResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminSummaryResponse;
import com.backend.allreva.rent.query.application.dto.RentDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentAdminJoinDetailResponse;
import com.backend.allreva.rent.query.application.dto.RentJoinSummaryResponse;
import com.backend.allreva.rent.query.application.dto.RentSummaryResponse;
import com.backend.allreva.survey.query.application.dto.SortType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentQueryRepository {
    List<RentSummaryResponse> findRentSummaries(Region region, SortType sortType, LocalDate lastEndDate, Long lastId,  int pageSize);
    Optional<RentDetailResponse> findRentDetailById(Long rentId);
    Optional<DepositAccountResponse> findDepositAccountById(Long rentId);

    List<RentAdminSummaryResponse> findRentAdminSummariesByMemberId(Long memberId);
    Optional<RentAdminDetailResponse> findRentAdminDetail(Long memberId, LocalDate boardingDate, Long rentId);
    List<RentAdminJoinDetailResponse> findRentAdminJoinDetails(Long memberId, Long rentId, LocalDate boardingDate);

    List<RentJoinSummaryResponse> findRentJoinSummariesByMemberId(Long memberId);
}
