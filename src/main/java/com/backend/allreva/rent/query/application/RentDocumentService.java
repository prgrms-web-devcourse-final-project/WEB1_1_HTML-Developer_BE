package com.backend.allreva.rent.query.application;

import com.backend.allreva.concert.exception.exception.ElasticSearchException;
import com.backend.allreva.concert.exception.exception.SearchResultNotFoundException;
import com.backend.allreva.rent.query.application.domain.RentDocument;
import com.backend.allreva.rent.query.application.domain.RentDocumentRepository;
import com.backend.allreva.rent.query.application.dto.RentThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
}
