package com.backend.allreva.concert.query.application;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class ConcertQueryServiceTest extends IntegralTestSupport {
//
//    @Autowired
//    ConcertQueryService concertQueryService;
//
//    @Test
//    @DisplayName("콘서트 메인 화면 조회 입력된 지역에 맞춰서 정렬 조건에 맞게 정렬 이후 마지막 searchAfter 보내면 다음 페이지에서 첫 페이지로")
//    void getConcertMain() {
//        List<Object> searchAfter = new ArrayList<>();
//        ConcertMainResponse concertMain = concertQueryService.getConcertMain("서울", searchAfter, 3, SortDirection.VIEWS);
//
//        concertMain.concertMain().forEach(
//                concertMain1 -> log.info("concertMain1: {}", concertMain1)
//        );
//        log.info("concertMain.searchAfter: {}", concertMain.searchAfter());
//
//        ConcertMainResponse concertMain1 = concertQueryService.getConcertMain("서울", concertMain.searchAfter(), 3, SortDirection.VIEWS);
//
//        concertMain1.concertMain().forEach(
//                concertMain2 -> log.info("concertMain2: {}", concertMain2)
//        );
//
//
//
//        ConcertMainResponse concertMain2 = concertQueryService.getConcertMain("서울", searchAfter, 6, SortDirection.VIEWS);
//        concertMain2.concertMain().forEach(
//                concertMain3 -> log.info("concertMain3: {}", concertMain3)
//        );
//
//        assertThat(concertMain.searchAfter().get(0), is(concertMain2.concertMain().get(0).stdate()));
//    }
}