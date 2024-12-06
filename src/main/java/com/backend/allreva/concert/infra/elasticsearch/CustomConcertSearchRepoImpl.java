package com.backend.allreva.concert.infra.elasticsearch;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.backend.allreva.concert.exception.search.ElasticSearchException;
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
        try {
            NativeQuery searchQuery = getNativeQuery(SearchField.ADDRESS,address, searchAfter, size, sortDirection);
            return elasticsearchOperations.search(searchQuery, ConcertDocument.class);
        }catch (ElasticSearchException e){
            throw new ElasticSearchException();
        }
    }

    @Override
    public SearchHits<ConcertDocument> searchByTitleList(
            final String query,
            final List<Object> searchAfter,
            final int size) {
        try{
            NativeQuery searchQuery = getNativeQuery(SearchField.TITLE, query, searchAfter, size, SortDirection.SCORE);
            return elasticsearchOperations.search(searchQuery, ConcertDocument.class);
        }catch (ElasticSearchException e){
            throw new ElasticSearchException();
        }
    }

    private NativeQuery getNativeQuery(
            final SearchField searchField,
            final String searchTerm,
            final List<Object> searchAfter,
            final int size,
            final SortDirection sortDirection) {

        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(buildSearchQuery(searchField, searchTerm))
                .withSort(buildPrimarySort(sortDirection))
                .withSort(buildSecondarySort())
                .withPageable(PageRequest.of(0, size));

        if(searchAfter != null && !searchAfter.isEmpty()){
            nativeQueryBuilder.withSearchAfter(searchAfter);
        }
        return nativeQueryBuilder.build();
    }

    private Query buildSearchQuery(final SearchField searchField, final String searchTerm) {
        if (!StringUtils.hasText(searchTerm)) {
            return Query.of(q -> q
                    .bool(b -> b
                            .filter(f -> f
                                    .range(r -> r
                                            .field("eddate")
                                            .gte(JsonData.of("now"))
                                    )
                            )
                    )
            );
        }

        // 검색어가 있는 경우의 퍼지 매칭 쿼리
        if (StringUtils.hasText(searchTerm)) {
            return Query.of(q -> q
                    .bool(b -> b
                            .must(m -> m
                                    .match(mt -> mt
                                            .field(searchField.getFieldName())
                                            .query(searchTerm)
                                            .fuzziness("AUTO")
                                    )
                            )
                            .filter(f -> f
                                    .range(r -> r
                                            .field("eddate")
                                            .gte(JsonData.of("now"))
                                    )
                            )
                    )
            );
        }

        return Query.of(q -> q.matchAll(m -> m));
    }


    private SortOptions buildPrimarySort(final SortDirection sortDirection) {
        return SortOptions.of(s -> s
                .field(f -> f
                        .field(getSortField(sortDirection))
                        .order(getSortOrder(sortDirection))
                )
        );
    }

    private SortOptions buildSecondarySort() {
        return SortOptions.of(s -> s
                .field(f -> f
                        .field("id")
                        .order(SortOrder.Asc)
                )
        );
    }

    private String getSortField(final SortDirection sortDirection) {
        return switch (sortDirection) {
            case DATE -> "stdate";
            case VIEWS -> "view_count";
            case SCORE -> "_score";
        };
    }

    private SortOrder getSortOrder(final SortDirection sortDirection) {
        return sortDirection == SortDirection.DATE ? SortOrder.Asc : SortOrder.Desc;
    }
}
