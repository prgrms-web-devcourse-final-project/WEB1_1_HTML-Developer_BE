package com.backend.allreva.concert.query.application;


import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.query.application.domain.value.SortDirection;
import com.backend.allreva.concert.exception.ConcertSearchNotFoundException;
import com.backend.allreva.concert.query.application.dto.ConcertDetailResponse;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.concert.query.application.domain.ConcertDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConcertQueryService {

    private final ConcertRepository concertRepository;
    private final ConcertSearchRepository concertSearchRepository;

    public ConcertDetailResponse findDetailById(final Long concertId) {
        concertRepository.increaseViewCount(concertId);
        return concertRepository.findDetailById(concertId);
    }

    public ConcertMainResponse getConcertMain(
            final String address,
            final List<Object> searchAfter,
            final int size,
            final SortDirection sortDirection) {

        SearchHits<ConcertDocument> searchHits = concertSearchRepository.searchMainConcerts(address, searchAfter, size +1, sortDirection);
        List<ConcertThumbnail> concerts = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(ConcertThumbnail::from)
                .limit(size)
                .toList();

        if(concerts.isEmpty()){
            throw new ConcertSearchNotFoundException();
        }

        boolean hasNext = searchHits.getSearchHits().size() > size;
        List<Object> nextSearchAfter = hasNext ?
                searchHits.getSearchHits().get(size - 1).getSortValues() : null;
        return ConcertMainResponse.from(concerts, nextSearchAfter);

    }

}
