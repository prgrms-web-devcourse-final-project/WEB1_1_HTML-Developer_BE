package com.backend.allreva.hall.query.application;

import com.backend.allreva.hall.command.domain.ConcertHallRepository;
import com.backend.allreva.hall.exception.ConcertHallNotFoundException;
import com.backend.allreva.hall.infra.elasticcsearch.ConcertHallSearchRepository;
import com.backend.allreva.hall.query.application.response.ConcertHallDetailResponse;
import com.backend.allreva.hall.query.application.response.ConcertHallMainResponse;
import com.backend.allreva.hall.query.application.response.ConcertHallThumbnail;
import com.backend.allreva.hall.query.domain.ConcertHallDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertHallQueryService {

    private final ConcertHallRepository concertHallRepository;
    private final ConcertHallSearchRepository concertHallSearchRepository;

    public ConcertHallDetailResponse findDetailByHallCode(final String hallCode) {
        return concertHallRepository.findDetailByHallCode(hallCode);
    }

    public ConcertHallMainResponse getConcertHallMain(
            final String address,
            final int seatScale,
            final List<Object> searchAfter,
            final int size
    ) {
        SearchHits<ConcertHallDocument> searchHits = concertHallSearchRepository.searchMainConcertHall(
                address,
                seatScale,
                searchAfter,
                size + 1
        );
        List<ConcertHallThumbnail> concertHall = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(ConcertHallThumbnail::from)
                .limit(size)
                .toList();

        if(concertHall.isEmpty()) {
            throw new ConcertHallNotFoundException();
        }

        boolean hasNext = searchHits.getSearchHits().size() > size;
        List<Object> nextSearchAfter = hasNext ?
                searchHits.getSearchHits().get(size -1).getSortValues() : null;
        return ConcertHallMainResponse.from(concertHall, nextSearchAfter);


    }
}
