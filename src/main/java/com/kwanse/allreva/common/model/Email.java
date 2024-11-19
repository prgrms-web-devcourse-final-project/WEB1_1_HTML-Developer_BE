package com.kwanse.allreva.common.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private String email;

    @Builder
    private Email(String email) {
        this.email = email;
    }
}
