package com.backend.allreva.concert.infra.repository;

import com.backend.allreva.concert.query.application.domain.value.SortDirection;
import com.backend.allreva.concert.query.application.domain.ConcertDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomConcertSearchRepo {
    SearchHits<ConcertDocument> searchMainConcerts(
            String address,
            List<Object> searchAfter,
            int size,
            SortDirection sortDirection);
    SearchHits<ConcertDocument> searchByTitleList(
            String query,
            List<Object> searchAfter,
            int size);
}
