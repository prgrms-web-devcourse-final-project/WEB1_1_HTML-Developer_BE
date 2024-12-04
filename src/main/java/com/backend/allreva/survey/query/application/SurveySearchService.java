package com.backend.allreva.survey.query.application;

import com.backend.allreva.concert.exception.exception.ElasticSearchException;
import com.backend.allreva.concert.exception.exception.SearchResultNotFoundException;
import com.backend.allreva.survey.query.application.domain.SurveyDocument;
import com.backend.allreva.survey.query.application.domain.SurveyDocumentRepository;
import com.backend.allreva.survey.query.application.dto.SurveyThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveySearchService {
    private final SurveyDocumentRepository surveyDocumentRepository;

    public List<SurveyThumbnail> searchSurveyThumbnails(final String title) {
        try {
            List<SurveyDocument> content = surveyDocumentRepository.findByTitleMixed(
                    title, PageRequest.of(0, 2)
            ).getContent();

            if (content.isEmpty()) {
                throw new SearchResultNotFoundException();
            }
            return content.stream()
                    .map(SurveyThumbnail::from)
                    .toList();
        } catch (ElasticSearchException e) {
            throw new ElasticSearchException();
        }
    }
}
