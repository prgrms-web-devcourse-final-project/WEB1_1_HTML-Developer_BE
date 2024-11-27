package com.backend.allreva.search.infra;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.backend.allreva.concert.command.domain.value.SortDirection;
import com.backend.allreva.search.query.domain.ConcertDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CustomConcertSearchRepoImpl implements CustomConcertSearchRepo {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<ConcertDocument> searchMainConcerts(
            final String address,
            final List<Object> searchAfter,
            final int size,
            final SortDirection sortDirection) {
        NativeQuery searchQuery = getNativeQuery(address, searchAfter, size, sortDirection);
        return elasticsearchOperations.search(searchQuery, ConcertDocument.class);
    }
    private static NativeQuery getNativeQuery(
            final String address,
            final List<Object> searchAfter,
            final int size,
            final SortDirection sortDirection) {
        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(q -> {
                    if (StringUtils.hasText(address)) {
                        return q.match(m -> m
                                .field("concert_hall_address.mixed")
                                .query(address)
                                .fuzziness("AUTO")
                        );
                    } else {
                        return q.matchAll(m -> m);
                    }
                })
                .withSort(s -> s
                        .field(f -> f
                                .field(sortDirection == SortDirection.DATE
                                        ? "stdate" : "view_count"
                                )
                                .order(SortOrder.Asc)
                        )
                )
                .withSort(s -> s
                        .field(f -> f
                                .field("concert_code")
                                .order(SortOrder.Desc)
                        )
                )
                .withPageable(PageRequest.of(0, size));

        if(searchAfter != null && !searchAfter.isEmpty()){
            nativeQueryBuilder.withSearchAfter(searchAfter);
        }
        return nativeQueryBuilder.build();
    }

}
