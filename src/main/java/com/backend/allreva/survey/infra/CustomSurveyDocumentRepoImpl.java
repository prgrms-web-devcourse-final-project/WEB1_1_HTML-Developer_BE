package com.backend.allreva.survey.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@RequiredArgsConstructor
public class CustomSurveyDocumentRepoImpl implements CustomSurveyDocumentRepo {
    private final ElasticsearchOperations elasticsearchOperations;

}
