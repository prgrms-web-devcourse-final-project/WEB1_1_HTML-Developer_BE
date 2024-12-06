package com.backend.allreva.survey.query.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {
    LATEST("최신순"),
    OLDEST("오래된순"),
    CLOSING("마감순");

    private final String korean;
}
