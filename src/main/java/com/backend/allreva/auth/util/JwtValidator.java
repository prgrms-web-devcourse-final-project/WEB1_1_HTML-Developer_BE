package com.backend.allreva.auth.util;

import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.allreva.auth.exception.code.ExpiredJwtTokenException;
import com.backend.allreva.auth.exception.code.InvalidJwtSignatureException;
import com.backend.allreva.auth.exception.code.InvalidJwtTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Slf4j
@Component
public class JwtValidator {

    private final SecretKey secretKey;

    public JwtValidator(@Value("${jwt.secret-key}") final String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public void validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new InvalidJwtTokenException();
        } catch (SignatureException e) {
            log.error("Invalid JWT token signature: {}", e.getMessage());
            throw new InvalidJwtSignatureException();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
            throw new ExpiredJwtTokenException();
        }
    }

    // TODO: refresh Token 검증 (redis 활용)
}
