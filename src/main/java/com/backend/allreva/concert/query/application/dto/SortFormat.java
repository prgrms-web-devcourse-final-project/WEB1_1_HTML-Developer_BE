package com.backend.allreva.concert.query.application.dto;

public enum SortFormat {

    LATEST("latest"),
    EARLIEST("earliest"),
    BY_DEADLINE("byDeadline"),;

    private final String orderBy;

    SortFormat(String orderBy) {
        this.orderBy = orderBy;
    }

    public static SortFormat from(String orderBy) {
        for (SortFormat sortFormat : SortFormat.values()) {
            if (sortFormat.orderBy.equals(orderBy)) {
                return sortFormat;
            }
        }
        return LATEST;
    }
}
