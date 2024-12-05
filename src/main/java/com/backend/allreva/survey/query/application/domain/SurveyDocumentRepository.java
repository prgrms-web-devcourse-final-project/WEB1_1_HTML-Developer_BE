package com.backend.allreva.survey.query.application.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SurveyDocumentRepository extends ElasticsearchRepository<SurveyDocument, String> {


}
