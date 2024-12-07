package com.backend.allreva.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class RefreshToken {

    @Id
    private String token;
    @Indexed
    private Long memberId;

    @Builder
    private RefreshToken(final String token, final Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}
