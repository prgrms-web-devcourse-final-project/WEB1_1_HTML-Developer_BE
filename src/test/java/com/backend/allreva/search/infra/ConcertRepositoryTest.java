package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.ConcertDocument;
import com.backend.allreva.support.IntegrationTestSupport;
import lombok.extern.slf4j.Slf4j;
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
    void test1(){
        PageRequest pageRequest = PageRequest.of(0, 2);

        List<ConcertDocument> day6 = concertSearchRepository.findByTitleMixed("day6", pageRequest).getContent();

        assertThat(day6.size(), is(2));
    }
}