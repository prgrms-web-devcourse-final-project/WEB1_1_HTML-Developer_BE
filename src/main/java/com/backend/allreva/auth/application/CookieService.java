package com.backend.allreva.auth.application;

import com.backend.allreva.common.util.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Value("${jwt.refresh.expiration}")
    private int refreshTime;

    public void addRefreshTokenCookie(
            HttpServletResponse response,
            String refreshToken
    ) {
        CookieUtils.addCookie(response, "refreshToken", refreshToken, refreshTime);
    }
}
