package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthService;
import com.backend.allreva.auth.application.CookieService;
import com.backend.allreva.auth.application.dto.LoginResponse;
import com.backend.allreva.auth.application.dto.ReissueRequest;
import com.backend.allreva.auth.application.dto.ReissueResponse;
import com.backend.allreva.common.dto.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerSwagger {

    private final AuthService authService;
    private final CookieService cookieService;

    @GetMapping("/login/kakao")
    public RedirectView redirectKakaoLogin() {
        return new RedirectView(authService.getLoginURL());
    }

    @GetMapping("/token/kakao")
    public Response<LoginResponse> authKakaoLogin(
            @RequestParam("code") final String authorizationCode,
            final HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.kakaoLogin(authorizationCode);
        if (loginResponse.isUser()) {
            cookieService.addRefreshTokenCookie(response, loginResponse.refreshToken());
        }
        return Response.onSuccess(loginResponse);
    }

    @PostMapping("/token/reissue")
    public Response<ReissueResponse> reissueToken(
            @RequestBody final ReissueRequest reissueRequest,
            final HttpServletResponse response
    ) {
        ReissueResponse reissueResponse = authService.reissueAccessToken(reissueRequest);
        cookieService.addRefreshTokenCookie(response, reissueResponse.refreshToken());
        return Response.onSuccess(reissueResponse);
    }
}
