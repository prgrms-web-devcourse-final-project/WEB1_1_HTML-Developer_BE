package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.SurveyDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SurveyDocumentRepository extends ElasticsearchRepository<SurveyDocument, String> {
}
