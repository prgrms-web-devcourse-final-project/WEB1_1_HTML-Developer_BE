package com.backend.allreva.auth.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findRefreshTokenByMemberId(Long memberId);
    boolean existsRefreshTokenByToken(String token);
}