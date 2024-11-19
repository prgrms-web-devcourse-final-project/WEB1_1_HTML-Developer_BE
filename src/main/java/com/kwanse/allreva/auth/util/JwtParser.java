package com.kwanse.allreva.auth.util;

import java.util.Arrays;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtParser {

    private final SecretKey secretKey;

    public JwtParser(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    /**
     * Request로부터 header에 있는 Access Token 추출 메서드
     */
    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Request로부터 cookie 혹은 header에 있는 Refresh Token 추출 메서드
     */
    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = null;
        if (cookies != null && cookies.length != 0) {
            refreshToken = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        // Cookie에 없다면 Header 재확인
        if (refreshToken != null) {
            return refreshToken;
        } else {
            return request.getHeader("refresh_token");
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
