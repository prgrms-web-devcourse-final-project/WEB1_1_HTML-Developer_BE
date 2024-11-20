package com.backend.allreva.common.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Image {

    private String originalName;
    private String storeName;
    private String imageUrl;

    public Image(String originalName, String storeName, String imageUrl) {
        this.originalName = originalName;
        this.storeName = storeName;
        this.imageUrl = imageUrl;
    }
}
