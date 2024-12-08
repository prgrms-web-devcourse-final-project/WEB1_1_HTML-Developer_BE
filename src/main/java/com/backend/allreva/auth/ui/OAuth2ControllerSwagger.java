package com.backend.allreva.auth.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 OAuth2 로그인 API")
public interface OAuth2ControllerSwagger {

    @Operation(
            summary = "oauth2 로그인",
            description = """
            <b>oauth2 로그인 API</b>
            
            먼저 로그인을 다음 주소로 login한 후 token 주소를 얻습니다.
            - ex) `/api/v1/oauth2/login/kakao`
            
            이후 token을 이용하여 회원가입 절차를 진행합니다.
            
            두 절차를 모두 거치면 USER 권한을 가진 Member로 등록됩니다.
            
            ⚠️ 주의: 테스트 환경에서는 oauth2 API가 유효하지 않습니다. 로컬 테스트일 경우 /test-developer로 개발자 모드를 활성화하세요.
            """
    )
    ResponseEntity<Void> login();
}
