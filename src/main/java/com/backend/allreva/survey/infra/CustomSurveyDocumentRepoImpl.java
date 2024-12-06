package com.backend.allreva.survey.infra;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.backend.allreva.concert.exception.search.ElasticSearchException;
import com.backend.allreva.survey.query.application.domain.SurveyDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CustomSurveyDocumentRepoImpl implements CustomSurveyDocumentRepo {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchHits<SurveyDocument> searchByTitleList(
            final String query,
            final List<Object> searchAfter,
            final int size
            ){
        try {
            NativeQuery nativeQuery = getNativeQuery(query, searchAfter, size);
            return elasticsearchOperations.search(nativeQuery, SurveyDocument.class);
        } catch (ElasticSearchException e) {
            throw new ElasticSearchException();
        }
    }

    private NativeQuery getNativeQuery(
            final String searchTerm,
            final List<Object> searchAfter,
            final int size
            ) {

        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(buildSearchQuery(searchTerm))
                .withSort(buildPrimarySort())
                .withSort(buildSecondarySort())
                .withPageable(PageRequest.of(0, size));

        if(searchAfter != null && !searchAfter.isEmpty()){
            nativeQueryBuilder.withSearchAfter(searchAfter);
        }
        return nativeQueryBuilder.build();
    }

    private Query buildSearchQuery(final String searchTerm) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        // 날짜 필터 추가
        boolQuery.filter(f -> f
                .range(r -> r
                        .field("eddate")
                        .gte(JsonData.of("now"))
                )
        );

        // 검색어가 있는 경우
        if (StringUtils.hasText(searchTerm)) {
            boolQuery.must(m -> m
                    .match(mt -> mt
                            .field("title.mixed")
                            .query(searchTerm)
                            .fuzziness("AUTO")
                    )
            );
        } else {
            boolQuery.must(m -> m
                    .matchAll(ma -> ma)
            );
        }

        return Query.of(q -> q.bool(boolQuery.build()));
    }


    private SortOptions buildPrimarySort() {
        return SortOptions.of(s -> s
                .score(f -> f
                        .order(SortOrder.Desc)
                )
        );
    }

    private SortOptions buildSecondarySort() {
        return SortOptions.of(s -> s
                .field(f -> f
                        .field("id")  // _id 필드로 정렬
                        .order(SortOrder.Asc)
                )
        );
    }
}
