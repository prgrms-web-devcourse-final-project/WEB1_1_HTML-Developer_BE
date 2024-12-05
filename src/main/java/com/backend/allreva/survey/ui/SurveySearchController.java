package com.backend.allreva.survey.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.survey.query.application.SurveySearchService;
import com.backend.allreva.survey.query.application.dto.SurveySearchListResponse;
import com.backend.allreva.survey.query.application.dto.SurveyThumbnail;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/surveys")
public class SurveySearchController {
    private final SurveySearchService surveySearchService;

    @GetMapping("/")
    @Operation(
            summary = "전체 검색시 수요조사 상위 2개 썸네일 API",
            description = "검색어에 따라 관련도 상위 2개의 썸네일에 필요한 정보를 출력"
    )
    public Response<List<SurveyThumbnail>> searchSurveyThumbnail(@RequestParam final String query) {
        return Response.onSuccess(
                surveySearchService.searchSurveyThumbnails(query)
        );
    }

    @GetMapping("/list")
    @Operation(
            summary = "수요조사 검색 더보기 API",
            description = "검색어에 따라 관련도 순으로 무한 스크롤 searchAfter1, searchAfter2에 이전 SearchAfter에 있는 값들을 순서대로 넣어주어야 합니다."
    )
    public Response<SurveySearchListResponse> searchSurveyList(
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


        return Response.onSuccess(
                surveySearchService.searchSurveyList(query, searchAfter, pageSize)
        );

    }
}
