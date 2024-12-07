package com.backend.allreva.common.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityEndpointPaths {

    public static final String[] WHITE_LIST = {
            "/h2-console/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api/v1/oauth2/login/**",
            "/login/oauth2/**"
    };

    public static final String[] ANOMYMOUS_LIST = {
            "/api/v1/search/**",
            "/api/v1/concerts/**",

    };

    public static final String[] ANOMYMOUS_LIST_GET = {
            "/api/v1/surveys/list",
            "/api/v1/surveys",
            "/api/v1/rents/list",
            "/api/v1/rents",
    };

    public static final String[] ADMIN_LIST = {
            "/api/v1/admin/**"
    };

    public static final String[] GUSET_LIST = {
            "/api/v1/oauth2/**"
    };
}
