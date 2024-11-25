package com.backend.allreva.search.infra;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.search.query.domain.ConcertDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CustomConcertSearchRepoImpl implements CustomConcertSearchRepo {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ConcertDocument> searchMainConcerts(String address, List<Object> searchAfter, int size, SortDirection sortDirection) {
        NativeQuery searchQuery = getNativeQuery(address, searchAfter, size, sortDirection);
        return elasticsearchOperations.search(searchQuery, ConcertDocument.class);
    }

    private static NativeQuery getNativeQuery(String address, List<Object> searchAfter, int size, SortDirection sortDirection) {
        return NativeQuery.builder()
                .withQuery(q -> {
                    if (StringUtils.hasText(address)) {
                        return q.match(m -> m
                                .field("concert_hall_address")
                                .query(address)
                        );
                    } else {
                        return q.matchAll(m -> m.queryName("match_all"));
                    }
                })
                .withSort(s -> s
                        .field(f -> f
                                .field(StringUtils.hasText(address) ? "stdate" : "id")
                                .order(sortDirection == SortDirection.ASC ? SortOrder.Asc : SortOrder.Desc)
                        )
                )
                .withSort(s -> s
                        .field(f -> f
                                .field("id")
                                .order(sortDirection == SortDirection.ASC ? SortOrder.Asc : SortOrder.Desc)
                        )
                )
                .withPageable(PageRequest.of(0, size + 1))
                .withSearchAfter(searchAfter)
                .build();
    }
}
