package com.backend.allreva.common.config;

import static com.backend.allreva.common.config.SecurityEndpointPaths.*;

import com.backend.allreva.auth.exception.CustomAccessDeniedHandler;
import com.backend.allreva.auth.exception.CustomAuthenticationEntryPoint;
import com.backend.allreva.auth.exception.JwtExceptionFilter;
import com.backend.allreva.auth.filter.JwtAuthenticationFilter;
import com.backend.allreva.auth.oauth2.application.CustomOAuth2UserService;
import com.backend.allreva.auth.oauth2.handler.OAuth2LoginFailureHandler;
import com.backend.allreva.auth.oauth2.handler.OAuth2LoginSuccessHandler;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    // OAuth2
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

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
                        .requestMatchers(ANOMYMOUS_LIST).permitAll()
                        .requestMatchers(ANOMYMOUS_LIST_GET).permitAll()
                        .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
                        .requestMatchers(GUSET_LIST).hasRole("GUEST")
                        .anyRequest().permitAll())
                .httpBasic(AbstractHttpConfigurer::disable);

        // OAuth2 인증 필터
        http
                .oauth2Login(customConfigurer -> customConfigurer
                        .authorizationEndpoint(end -> end.baseUri("/api/v1/oauth2/login"))
                        .userInfoEndpoint(endPointConfig -> endPointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler));

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
