package com.backend.allreva.common.config;

import static org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.fromHierarchy;

import com.backend.allreva.auth.exception.CustomAccessDeniedHandler;
import com.backend.allreva.auth.exception.CustomAuthenticationEntryPoint;
import com.backend.allreva.auth.filter.TestAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("local")
public class SecurityTestConfig {

    // 인가 과정에서 허용할 URL 등록
    private static final String[] ALLOW_URLS = {
            "/h2-console/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**"
    };

    private final TestAuthenticationFilter testAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                .httpBasic(HttpBasicConfigurer<HttpSecurity>::disable)
                .headers(it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ALLOW_URLS).permitAll()
                        .requestMatchers("/test-developer").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(testAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return fromHierarchy(
                """
                ROLE_DEVELOPER > ROLE_ADMIN
                ROLE_ADMIN > ROLE_USER
                ROLE_USER > ROLE_GUEST
                """
        );
    }
}
