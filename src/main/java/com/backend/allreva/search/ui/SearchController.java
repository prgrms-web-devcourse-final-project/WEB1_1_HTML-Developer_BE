package com.backend.allreva.search.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.query.application.dto.ConcertThumbnail;
import com.backend.allreva.search.query.application.ConcertSearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
