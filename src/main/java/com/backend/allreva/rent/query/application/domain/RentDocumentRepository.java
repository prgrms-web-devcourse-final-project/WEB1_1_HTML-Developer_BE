package com.backend.allreva.rent.query.application.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RentDocumentRepository extends ElasticsearchRepository<RentDocument, String> {
    @Query("{\"match\": {\"title.mixed\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    Page<RentDocument> findByTitleMixed(String title, Pageable pageable);
}
