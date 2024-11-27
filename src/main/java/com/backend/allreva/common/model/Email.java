package com.backend.allreva.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Email {

    @Column(nullable = false)
    private String email;

    @Builder
    private Email(final String email) {
        this.email = email;
    }
}
