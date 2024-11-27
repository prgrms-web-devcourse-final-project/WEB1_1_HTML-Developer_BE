package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.ConcertDocument;
import com.backend.allreva.search.query.domain.CustomConcertSearchRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertSearchRepository extends
        ElasticsearchRepository<ConcertDocument, String>,
        CustomConcertSearchRepo {
    @Query("{\"match\": {\"title.mixed\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<ConcertDocument> findByTitleWithFuzziness(String title, Pageable pageable);

}
