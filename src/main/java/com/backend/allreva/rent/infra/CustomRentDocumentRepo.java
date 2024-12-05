package com.backend.allreva.rent.infra;

import com.backend.allreva.rent.query.application.domain.RentDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface CustomRentDocumentRepo {
    SearchHits<RentDocument> searchByTitleList(
            final String query,
            final List<Object> searchAfter,
            final int size
    );
}
