package com.backend.allreva.search.infra;

import com.backend.allreva.IntegralTestSupport;
import com.backend.allreva.search.query.domain.ConcertDocument;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Slf4j
class ConcertRepositoryTest extends IntegralTestSupport{

    @Autowired
    private ConcertSearchRepository concertSearchRepository;


    @Test
    void test1(){
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<ConcertDocument> day6 = concertSearchRepository.findByTitleWithFuzziness("day6", pageRequest);
        System.out.println(day6.size());
        for (ConcertDocument concertDocument : day6) {
            log.info("concert: {}", concertDocument.toString());
        }
    }
}