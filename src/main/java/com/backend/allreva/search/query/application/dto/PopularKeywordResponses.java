package com.backend.allreva.search.query.application.dto;

import java.util.ArrayList;
import java.util.List;

public record PopularKeywordResponses(List<PopularKeywordResponse> popularKeywordResponses) {
    public PopularKeywordResponses(List<PopularKeywordResponse> popularKeywordResponses) {
        this.popularKeywordResponses = popularKeywordResponses != null ? popularKeywordResponses : new ArrayList<>();
    }
}
