package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "OAuth2 로그인 API")
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

    @Operation(summary = "oauth2 회원가입", description = "oauth2 회원가입 시 USER 권한으로 승격됩니다.")
    @RequestBody(
            content = @Content(
                    encoding = @Encoding(
                            name = "memberInfoRequest", contentType = MediaType.APPLICATION_JSON_VALUE)))
    ResponseEntity<Void> registerMember(
            @AuthMember Member member,
            @RequestPart MemberInfoRequest memberInfoRequest,
            @RequestPart(value = "image", required = false) MultipartFile image
    );
}
