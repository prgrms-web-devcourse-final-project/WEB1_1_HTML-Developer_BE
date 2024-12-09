package com.backend.allreva.survey.infra.elasticsearch;

import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomSurveyDocumentRepo {
    SearchHits<SurveyDocument> searchByTitleList(
            final String query,
            final List<Object> searchAfter,
            final int size
    );
}
