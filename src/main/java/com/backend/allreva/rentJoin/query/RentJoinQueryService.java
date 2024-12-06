package com.backend.allreva.rentJoin.query;

import com.backend.allreva.rentJoin.query.response.RentJoinSummaryResponse;
import com.backend.allreva.rentJoin.command.domain.RentJoinRepository;
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
