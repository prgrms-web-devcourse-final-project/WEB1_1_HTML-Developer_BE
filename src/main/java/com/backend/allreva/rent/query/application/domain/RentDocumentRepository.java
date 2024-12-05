package com.backend.allreva.rent.query.application.domain;

import com.backend.allreva.rent.infra.CustomRentDocumentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RentDocumentRepository extends
        ElasticsearchRepository<RentDocument, String>,
        CustomRentDocumentRepo {
    @Query("{\"match\": {\"title.mixed\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    Page<RentDocument> findByTitleMixed(String title, Pageable pageable);
}
