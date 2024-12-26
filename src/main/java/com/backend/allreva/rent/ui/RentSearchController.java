package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.RentSearchService;
import com.backend.allreva.rent.query.application.response.RentSearchListResponse;
import com.backend.allreva.rent.query.application.response.RentThumbnail;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search/rents")
@RequiredArgsConstructor
public class RentSearchController implements RentSearchControllerSwagger {
    private final RentSearchService rentSearchService;

    @GetMapping("/")
    public Response<List<RentThumbnail>> searchRentThumbnail(@RequestParam final String query) {
        return Response.onSuccess(
                rentSearchService.searchRentThumbnails(query)
        );
    }

    @GetMapping("/list")
    public Response<RentSearchListResponse> searchRentList(
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
                rentSearchService.searchRentSearchList(query, searchAfter, pageSize)
        );
    }
}
