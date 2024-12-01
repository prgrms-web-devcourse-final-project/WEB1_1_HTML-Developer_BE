package com.backend.allreva.search.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.search.query.application.ConcertSearchService;
import com.backend.allreva.search.query.application.dto.ConcertSearchListResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/avi/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final ConcertSearchService concertSearchService;

    @Operation(
            summary = "콘서트 검색시 상위 2개 썸네일 API",
            description = "콘서트 검색어에 따라 관련도 상위 2개의 썸네일에 필요한 정보를 출력"
    )
    @GetMapping("/concert")
    public Response<List<ConcertThumbnail>> searchConcertThumbnail(@RequestParam final String query) {
        return Response.onSuccess(
                concertSearchService.searchConcertThumbnails(query)
        );
    }

    @Operation(
            summary = "콘서트 검색 더보기 API",
            description = """
                    콘서트 검색어에 따라 관련도 순으로 무한 스크롤\s
                    searchAfter1, searchAfter2에 이전 SearchAfter에 있는 값들을 순서대로 넣어주어야 합니다.
                    """
    )
    @GetMapping("/concert/list")
    public Response<ConcertSearchListResponse> searchConcertList(
            @RequestParam
            @NotEmpty(message = "검색어를 입력해야 합니다.")
            final String query,
            @RequestParam(defaultValue = "7")
            final int pageSize,
            @RequestParam(required = false)
            final String searchAfter1,
            @RequestParam(required = false)
            final String searchAfter2
    ){
        List<Object> searchAfter = Stream.of(searchAfter1, searchAfter2)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        ConcertSearchListResponse response = concertSearchService.searchConcertList(query, searchAfter, pageSize);
        return Response.onSuccess(response);
    }
}
