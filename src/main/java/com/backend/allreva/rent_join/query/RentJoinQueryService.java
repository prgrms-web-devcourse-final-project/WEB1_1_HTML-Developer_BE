package com.backend.allreva.rent_join.query;

import com.backend.allreva.rent_join.query.response.RentJoinSummaryResponse;
import com.backend.allreva.rent_join.command.domain.RentJoinRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentJoinQueryService {

    private final RentJoinRepository rentJoinRepository;

    public List<RentJoinSummaryResponse> getRentJoinSummariesByMemberId(final Long memberId) {
        return rentJoinRepository.findRentJoinSummariesByMemberId(memberId);
    }
}
