package com.backend.allreva.search.query.application;

import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.search.exception.ElasticSearchException;
import com.backend.allreva.search.exception.SearchResultNotFoundException;
import com.backend.allreva.search.infra.ConcertSearchRepository;
import com.backend.allreva.search.query.domain.ConcertDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSearchService {
    private final ConcertSearchRepository concertSearchRepository;

    public List<ConcertThumbnail> searchConcertThumbnails(final String title) {
        try {
            List<ConcertDocument> content = concertSearchRepository.findByTitleMixed(
                    title, PageRequest.of(0, 2)).getContent();
            if (content.isEmpty()) {
                throw new SearchResultNotFoundException();
            }
            return content.stream()
                    .map(ConcertThumbnail::from)
                    .toList();
        }catch (ElasticSearchException e){
            throw new ElasticSearchException();
        }
    }
}
