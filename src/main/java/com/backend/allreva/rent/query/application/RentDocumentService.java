package com.backend.allreva.rent.query.application;


import com.backend.allreva.concert.exception.search.ElasticSearchException;
import com.backend.allreva.rent.query.application.domain.RentDocument;
import com.backend.allreva.rent.query.application.domain.RentDocumentRepository;
import com.backend.allreva.rent.query.application.dto.RentSearchListResponse;
import com.backend.allreva.rent.query.application.dto.RentThumbnail;
import com.backend.allreva.survey.exception.SearchResultNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentDocumentService {
    private final RentDocumentRepository rentDocumentRepository;

    public List<RentThumbnail> searchRentThumbnails(String title) {
        try {
            List<RentDocument> content = rentDocumentRepository.findByTitleMixed(
                    title, PageRequest.of(0, 2)
            ).getContent();

            if (content.isEmpty()) {
                throw new SearchResultNotFoundException();
            }
            return content.stream()
                    .map(RentThumbnail::from)
                    .toList();
        } catch (ElasticSearchException e) {
            throw new ElasticSearchException();
        }
    }

    public RentSearchListResponse searchRentSearchList(
            final String title,
            final List<Object> searchAfter,
            final int size
    ){
        SearchHits<RentDocument> searchHits = rentDocumentRepository
                .searchByTitleList(title, searchAfter, size + 1);

        List<RentThumbnail> rentThumbnails = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(RentThumbnail::from)
                .limit(size)
                .toList();

        if (rentThumbnails.isEmpty()) {
            throw new SearchResultNotFoundException();
        }

        boolean hasNext = searchHits.getSearchHits().size() > size;
        List<Object> nextSearchAfter = hasNext ?
                searchHits.getSearchHits().get(size - 1).getSortValues() : null;

        return RentSearchListResponse.from(rentThumbnails, nextSearchAfter);
    }
}
