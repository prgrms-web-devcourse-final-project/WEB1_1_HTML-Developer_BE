package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.dto.LoginResponse;
import com.backend.allreva.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "회원 인증 및 인가 API", description = "인증 및 인가를 처리합니다")
public interface AuthControllerSwagger {

    @Operation(summary = "kakao oauth2 login URL 조회", description = "카카오 로그인 URL을 조회합니다.")
    RedirectView getKakaoLoginUrl();

    @Operation(summary = "kakao oauth2 login 요청", description = "카카오 로그인을 수행합니다.")
    Response<LoginResponse> loginKakao(
            String authorizationCode,
            HttpServletResponse response
    );
}
