package com.backend.allreva.search.query.application;

import com.backend.allreva.search.query.application.dto.ChangeStatus;

import java.util.List;
import java.util.Set;

public interface PopularKeywordRepository {
    List<String> getPopularKeyword();

    void updateKeywordCount(String keyword, Double count);

    Set<String> getAllOneHourKeywords();

    Double getOneHourKeywordScore(String keyword);

    String getKeywordChangeStatus(String keyword);

    void setKeywordChangeStatus(String keyword, ChangeStatus status);

    void decreaseAllKeywordCount();

    void deleteOneHourKeyword();
}
