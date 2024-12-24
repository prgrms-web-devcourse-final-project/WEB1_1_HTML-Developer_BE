package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthService;
import com.backend.allreva.auth.application.CookieService;
import com.backend.allreva.auth.application.dto.ReissueRequest;
import com.backend.allreva.auth.application.dto.UserInfoResponse;
import com.backend.allreva.common.dto.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerSwagger {

    private final AuthService authService;
    private final CookieService cookieService;

    @GetMapping("/token/kakao")
    public Response<UserInfoResponse> authKakaoLogin(
            @RequestParam("code") final String authorizationCode,
            final HttpServletResponse response
    ) {
        UserInfoResponse userInfoResponse = authService.kakaoLogin(authorizationCode);
        if (userInfoResponse.isUser()) {
            cookieService.addRefreshTokenCookie(response, userInfoResponse.refreshToken());
            response.addHeader("Authorization", "Bearer " + userInfoResponse.accessToken());
        }
        return Response.onSuccess(userInfoResponse);
    }

    @PostMapping("/token/reissue")
    public Response<UserInfoResponse> reissueToken(
            @RequestBody final ReissueRequest reissueRequest,
            final HttpServletResponse response
    ) {
        UserInfoResponse userInfoResponse = authService.reissueAccessToken(reissueRequest);
        cookieService.addRefreshTokenCookie(response, userInfoResponse.refreshToken());
        response.addHeader("Authorization", "Bearer " + userInfoResponse.accessToken());
        return Response.onSuccess(userInfoResponse);
    }
}
