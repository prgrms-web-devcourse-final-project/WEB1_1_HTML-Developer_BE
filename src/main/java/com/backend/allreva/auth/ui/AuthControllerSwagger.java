package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.dto.LoginResponse;
import com.backend.allreva.auth.application.dto.ReissueRequest;
import com.backend.allreva.auth.application.dto.ReissueResponse;
import com.backend.allreva.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "회원 인증 및 인가 API", description = "인증 및 인가를 처리합니다")
public interface AuthControllerSwagger {

    @Operation(
            summary = "kakao oauth2 login URL 조회",
            description = "카카오 로그인 URL을 조회합니다.\n"
                    + "/api/v1/auth/login/kakao로 요청하면 바로 카카오 로그인 페이지로 리다이렉트 됩니다."
    )
    RedirectView redirectKakaoLogin();

    @Operation(summary = "kakao oauth2 login 요청", description = "카카오 로그인을 수행합니다.\n"
            + "먼저 카카오 로그인을 한 후에 authorization code를 받아서 이를 파라미터로 넘겨야 합니다.")
    Response<LoginResponse> authKakaoLogin(
            String authorizationCode,
            HttpServletResponse response
    );

    @Operation(summary = "access token 재발급 요청", description = "refresh token을 이용하여 access token을 재발급합니다.")
    Response<ReissueResponse> reissueToken(
            ReissueRequest reissueRequest,
            HttpServletResponse response
    );
}
