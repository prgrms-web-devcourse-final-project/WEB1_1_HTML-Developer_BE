package com.backend.allreva.common.config;

import static com.backend.allreva.common.config.SecurityEndpointPaths.ADMIN_LIST;
import static com.backend.allreva.common.config.SecurityEndpointPaths.ANONYMOUS_LIST;
import static com.backend.allreva.common.config.SecurityEndpointPaths.ANONYMOUS_LIST_GET;
import static com.backend.allreva.common.config.SecurityEndpointPaths.USER_LIST_GET;
import static com.backend.allreva.common.config.SecurityEndpointPaths.WHITE_LIST;

import com.backend.allreva.auth.security.CustomAccessDeniedHandler;
import com.backend.allreva.auth.security.CustomAuthenticationEntryPoint;
import com.backend.allreva.auth.security.JwtAuthenticationFilter;
import com.backend.allreva.auth.security.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("!local")
public class SecurityConfig {

    // JWT
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                .httpBasic(HttpBasicConfigurer<HttpSecurity>::disable)
                .headers(it -> it.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, USER_LIST_GET).hasRole("USER")
                        .requestMatchers(ANONYMOUS_LIST).permitAll()
                        .requestMatchers(HttpMethod.GET, ANONYMOUS_LIST_GET).permitAll()
                        .anyRequest().authenticated());

        // jwt 인증 필터
        http
                .addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
