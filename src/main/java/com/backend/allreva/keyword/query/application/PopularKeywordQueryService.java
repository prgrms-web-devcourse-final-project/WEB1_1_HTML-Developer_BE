package com.backend.allreva.keyword.query.application;

import com.backend.allreva.keyword.infra.PopularKeywordRepository;
import com.backend.allreva.keyword.query.application.dto.PopularKeywordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularKeywordQueryService {
    private final PopularKeywordRepository popularKeywordRepository;

    /**
     * 인기 검색어 Top 10 조회
     */
    public List<PopularKeywordResponse> getPopularKeywordRank() {

        return popularKeywordRepository.getPopularKeywordRank().popularKeywordResponses();
    }
}
