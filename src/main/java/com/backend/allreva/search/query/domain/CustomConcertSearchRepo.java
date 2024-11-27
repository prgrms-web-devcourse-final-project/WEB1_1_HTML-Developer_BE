package com.backend.allreva.search.query.domain;

import com.backend.allreva.concert.command.domain.value.SortDirection;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomConcertSearchRepo {
    SearchHits<ConcertDocument> searchMainConcerts(String address, List<Object> searchAfter, int size, SortDirection sortDirection);
}
