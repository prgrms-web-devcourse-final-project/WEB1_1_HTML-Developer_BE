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

    public static final String[] ANONYMOUS_LIST = {
            "/api/v1/search/**",
            "/api/v1/concerts/**",

    };

    public static final String[] USER_LIST_GET = {
            "/api/v1/rents/*/deposit-account",
            "/api/v1/rents/register/list",
            "/api/v1/rents/*/register",
            "/api/v1/rents/join/list",
            "/api/v1/serveys/member/list",
            "/api/v1/serveys/member/apply/list",
    };

    public static final String[] ANONYMOUS_LIST_GET = {
            "/api/v1/surveys/**", "/api/v1/rents/**"
    };

    public static final String[] ADMIN_LIST = {
            "/api/v1/admin/**"
    };

    public static final String[] GUEST_LIST = {
            "/api/v1/members/register"
    };
}
