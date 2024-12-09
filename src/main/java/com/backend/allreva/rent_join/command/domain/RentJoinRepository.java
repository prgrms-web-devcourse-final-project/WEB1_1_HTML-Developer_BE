package com.backend.allreva.rent_join.command.domain;

import com.backend.allreva.rent_join.query.response.RentJoinSummaryResponse;
import java.util.List;
import java.util.Optional;

public interface RentJoinRepository {

    Optional<RentJoin> findById(Long id);

    boolean existsById(Long id);

    RentJoin save(RentJoin rentJoin);

    void delete(RentJoin rentJoin);

    List<RentJoinSummaryResponse> findRentJoinSummariesByMemberId(Long memberId);
}
