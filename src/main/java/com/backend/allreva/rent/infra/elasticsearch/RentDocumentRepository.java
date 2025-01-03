package com.backend.allreva.rent.infra.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RentDocumentRepository extends
        ElasticsearchRepository<RentDocument, String>,
        CustomRentDocumentRepo {
    @Query("""
    {
        "bool": {
            "must": {
                "match": {
                    "title.mixed": {
                        "query": "?0",
                        "fuzziness": "AUTO"
                    }
                }
            },
            "filter": {
                "range": {
                    "eddate": {
                        "gte": "now/d",
                        "format": "strict_date_optional_time"
                    }
                }
            }
        }
    }
""")
    Page<RentDocument> findByTitleMixed(String title, Pageable pageable);

}
