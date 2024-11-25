package com.backend.allreva.auth.util;


import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTestTokenInitializer {

    private final Map<String, String> jwtTokens;

    public JwtTestTokenInitializer(JwtProvider jwtProvider) {
        this.jwtTokens = new HashMap<>();

        jwtTokens.put("ADMIN", jwtProvider.generateRefreshToken(String.valueOf(1L)));
        jwtTokens.put("USER", jwtProvider.generateRefreshToken(String.valueOf(2L)));
        jwtTokens.put("GUEST", jwtProvider.generateRefreshToken(String.valueOf(3L)));

        jwtTokens.forEach((role, token) ->
                log.info("{} Token: {}", role, token));
    }

    public String getToken(String role) {
        return jwtTokens.get(role);
    }
}
