package com.backend.allreva.rentJoin.command.domain;

import com.backend.allreva.rentJoin.query.response.RentJoinSummaryResponse;
import java.util.List;
import java.util.Optional;

public interface RentJoinRepository {

    Optional<RentJoin> findById(Long id);

    boolean existsById(Long id);

    RentJoin save(RentJoin rentJoin);

    void delete(RentJoin rentJoin);

    List<RentJoinSummaryResponse> findRentJoinSummariesByMemberId(Long memberId);
}
