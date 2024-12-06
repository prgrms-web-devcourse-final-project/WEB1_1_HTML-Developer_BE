package com.backend.allreva.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken {

    @Id
    private String token;
    private Long memberId;

    @Builder
    private RefreshToken(final String token, final Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}
