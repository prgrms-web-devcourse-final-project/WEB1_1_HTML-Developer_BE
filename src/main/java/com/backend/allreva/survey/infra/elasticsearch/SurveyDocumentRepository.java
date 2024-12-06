package com.backend.allreva.survey.infra.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SurveyDocumentRepository extends
        ElasticsearchRepository<SurveyDocument, String>,
        CustomSurveyDocumentRepo {
    @Query("{\"match\": {\"title.mixed\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    Page<SurveyDocument> findByTitleMixed(String title, Pageable pageable);
}

