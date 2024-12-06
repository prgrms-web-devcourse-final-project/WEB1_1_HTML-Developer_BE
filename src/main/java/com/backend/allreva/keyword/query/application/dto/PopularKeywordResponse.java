package com.backend.allreva.keyword.query.application.dto;

import lombok.Builder;

@Builder
public record PopularKeywordResponse(
        int rank,
        String keyword,
        ChangeStatus changeStatus) {

}
