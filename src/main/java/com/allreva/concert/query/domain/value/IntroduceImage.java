package com.allreva.concert.query.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class IntroduceImage {

    private String url;

    public IntroduceImage(final String url) {
        this.url = url;
    }
}
