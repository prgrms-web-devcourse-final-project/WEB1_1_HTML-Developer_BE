package com.backend.allreva.concert.query.application;


import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.dto.ConcertDetail;
import com.backend.allreva.concert.query.application.dto.ConcertMain;
import com.backend.allreva.concert.query.application.dto.ConcertMainResponse;
import com.backend.allreva.search.infra.ConcertSearchRepository;
import com.backend.allreva.search.query.domain.ConcertDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    private final ConcertSearchRepository concertSearchRepository;

    public ConcertDetail findDetailById(Long concertId) {
        return concertRepository.findDetailById(concertId);
    }

    public ConcertMainResponse getConcertMain(
            final String address,
            final List<Object> searchAfter,
            final int size,
            final SortDirection sortDirection) {

        try {
            SearchHits<ConcertDocument> searchHits = concertSearchRepository.searchMainConcerts(address, searchAfter, size +1, sortDirection);
            List<ConcertMain> concerts = searchHits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .map(ConcertMain::from)
                    .limit(size)
                    .toList();

            boolean hasNext = searchHits.getSearchHits().size() > size;
            List<Object> nextSearchAfter = hasNext ?
                    searchHits.getSearchHits().get(size - 1).getSortValues() : null;
            return ConcertMainResponse.from(concerts, nextSearchAfter);

        } catch (ElasticsearchException e) {
            log.error("ElasticsearchException : {}", e.getMessage());
            throw new CustomException(GlobalErrorCode.SERVER_ERROR);
        }
    }

}
