package com.backend.allreva.rent.query;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.rent.command.domain.RentFormRepository;
import com.backend.allreva.rent.query.application.RentFormQueryService;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class RentFormSummaryReadTest extends IntegrationTestSupport {

    @Autowired
    private RentFormQueryService rentFormQueryService;
    @Autowired
    private RentFormRepository rentFormRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertHallRepository concertHallRepository;

    @Test
    //@Transactional
    void 차량_대절_폼_리스트_조회를_성공한다() {
        // TODO: 차량 대절 폼 리스트 조회 구현 후 테스트 작성 예정
    }
}
