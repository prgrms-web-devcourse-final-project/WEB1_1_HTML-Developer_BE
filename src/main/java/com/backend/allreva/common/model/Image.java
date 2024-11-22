package com.backend.allreva.common.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Image {
    private String url;
    public Image(final String url) {
        this.url = url;
    }
}
