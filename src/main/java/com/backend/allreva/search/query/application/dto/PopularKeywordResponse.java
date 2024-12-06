package com.backend.allreva.search.query.application.dto;

import lombok.Builder;

@Builder
public record PopularKeywordResponse(
        int rank,
        String keyword,
        ChangeStatus changeStatus) {

}
