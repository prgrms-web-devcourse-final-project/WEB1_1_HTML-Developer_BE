package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.ConcertDocument;
import com.backend.allreva.support.IntegrationTestSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class ConcertRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ConcertSearchRepository concertSearchRepository;


    @Test
    @DisplayName("검색어 에 따라 연관성 상위 2개가 출력")
    void findByTitleMixedTest(){
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        List<ConcertDocument> day6 = concertSearchRepository.findByTitleMixed("day6", pageRequest).getContent();

        //then
        assertThat(day6.size(), is(2));
    }
}