package com.backend.allreva.common.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {

    @Value("${url.front.protocol}")
    private String frontProtocol;

    @Value("${url.front.domain}")
    private String frontDomain;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출처 패턴 설정
        configuration.setAllowedOriginPatterns(List.of(
                "https://localhost:3000",
                frontProtocol + "://" + frontDomain
        ));

        // 자격 증명 허용
        configuration.setAllowCredentials(true);

        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 헤더 설정
        configuration.setAllowedHeaders(List.of("*"));

        // 노출할 헤더 설정
        configuration.setExposedHeaders(List.of("Authorization"));

        // 최대 캐시 시간 설정 (초 단위)
        configuration.setMaxAge(3600L);

        // 모든 경로에 대해 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
