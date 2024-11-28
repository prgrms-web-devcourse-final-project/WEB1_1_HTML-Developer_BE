package com.backend.allreva.search.infra;

import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.search.query.domain.ConcertDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomConcertSearchRepo {
    SearchHits<ConcertDocument> searchMainConcerts(String address, List<Object> searchAfter, int size, SortDirection sortDirection);
}