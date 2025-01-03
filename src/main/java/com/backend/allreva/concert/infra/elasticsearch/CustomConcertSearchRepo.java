package com.backend.allreva.concert.infra.elasticsearch;

import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomConcertSearchRepo {
    SearchHits<ConcertDocument> searchMainConcerts(
            String address,
            List<Object> searchAfter,
            int size,
            SortDirection sortDirection
    );
    SearchHits<ConcertDocument> searchByTitleList(
            String query,
            List<Object> searchAfter,
            int size
    );

    SearchHits<ConcertDocument> searchByTitleListAll(
            String query,
            List<Object> searchAfter,
            int size
    );
}
