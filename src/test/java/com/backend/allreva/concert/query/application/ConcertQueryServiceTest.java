package com.backend.allreva.concert.query.application;

import com.backend.allreva.concert.query.application.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import com.backend.allreva.support.IntegrationTestSupport;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
class ConcertQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    ConcertQueryService concertQueryService;

    @Test
    @DisplayName("콘서트 메인 화면 조회 입력된 지역에 맞춰서 정렬 조건에 맞게 정렬 이후 마지막 searchAfter 보내면 다음 페이지에서 첫 페이지로")
    void getConcertMain() {
        List<Object> searchAfter = new ArrayList<>();
        ConcertMainResponse concertMain = concertQueryService.getConcertMain("서울", searchAfter, 3, SortDirection.VIEWS);

        concertMain.concertThumbnails().forEach(
                concertMain1 -> log.info("concertMain1: {}", concertMain1)
        );
        log.info("concertMain.searchAfter: {}", concertMain.searchAfter());

        ConcertMainResponse concertMain1 = concertQueryService.getConcertMain("서울", concertMain.searchAfter(), 3, SortDirection.VIEWS);

        concertMain1.concertThumbnails().forEach(
                concertMain2 -> log.info("concertMain2: {}", concertMain2)
        );
        log.info("concertMain1.searchAfter: {}", concertMain1.searchAfter());

        ConcertMainResponse concertMain2 = concertQueryService.getConcertMain("서울", searchAfter, 6, SortDirection.VIEWS);
        concertMain2.concertThumbnails().forEach(
                concertMain3 -> log.info("concertMain3: {}", concertMain3)
        );

        assertThat(concertMain2.concertThumbnails().get(3), Matchers.samePropertyValuesAs(concertMain2.concertThumbnails().get(0)));
    }
}