package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.response.RentSearchListResponse;
import com.backend.allreva.rent.query.application.response.RentThumbnail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "차량 대절 검색 API")
public interface RentSearchControllerSwagger {

    @Operation(
            summary = "차대절 검색시 상위 2개 썸네일 API",
            description = "차대절 검색어에 따라 관련도 상위 2개의 썸네일에 필요한 정보를 출력\n"
    )
    Response<List<RentThumbnail>> searchRentThumbnail(String query);

    @Operation(
            summary = "차대절 검색 더보기 API",
            description = "검색어에 따라 관련도 순으로 무한 스크롤 searchAfter1, searchAfter2에 이전 SearchAfter에 있는 값들을 순서대로 넣어주어야 합니다."
    )
   Response<RentSearchListResponse> searchRentList(
            String query,
            int pageSize,
            String searchAfter1,
            String searchAfter2
    );
}
