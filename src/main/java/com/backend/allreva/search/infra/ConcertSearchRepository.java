package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.ConcertDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcertSearchRepository extends
        ElasticsearchRepository<ConcertDocument, String>,
        CustomConcertSearchRepo {
    @Query("{\"match\": {\"title.mixed\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    Page<ConcertDocument> findByTitleMixed(String title, Pageable pageable);

    Optional<ConcertDocument> findByConcertCode(String concertCode);
}
