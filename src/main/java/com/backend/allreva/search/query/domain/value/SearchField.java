package com.backend.allreva.search.query.domain.value;

public enum SearchField {
    ADDRESS("concert_hall_address.mixed"),
    TITLE("title.mixed");

    private final String fieldName;

    SearchField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
