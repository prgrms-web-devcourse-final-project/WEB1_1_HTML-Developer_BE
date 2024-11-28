package com.backend.allreva.search.query.application;

import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.search.exception.SearchResultNotFoundException;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcertSearchServiceTest extends IntegrationTestSupport {
    @Autowired
    ConcertSearchService concertSearchService;


    @Test
    @DisplayName("정상적인 검색")
    void successTest() {
        //give
        //when
        List<ConcertThumbnail> result = concertSearchService.searchConcertThumbnails("day6");

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("검색 결과가 없는 경우 예외 발생")
    void NotFoundExceptionTest() {
        //then
        assertThrows(SearchResultNotFoundException.class, () -> {
            concertSearchService.searchConcertThumbnails("");
        });
    }
}