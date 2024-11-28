package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuth2Controller {

    private final MemberCommandFacade memberCommandFacade;

    @Operation(summary = "oauth2 로그인", description = """
            <b>oauth2 로그인 API</b>
            먼저 로그인을 다음 주소로 login한 후 token 주소를 얻습니다.
            - `http://{host}:{port}/api/v1/oauth2/login/{provider}`
            
            이후 token을 이용하여 회원가입 절차를 진행합니다.
            
            두 절차를 모두 거치면 USER 권한을 가진 Member로 등록됩니다.
            
            ⚠️ 주의: 테스트 환경에서는 oauth2 API가 유효하지 않습니다. 로컬 테스트일 경우 /test-developer로 개발자 모드를 활성화하세요.
            """)
    @GetMapping("/login/provider")
    public ResponseEntity<Void> login() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "oauth2 회원가입", description = "oauth2 회원가입 시 회원 정보 입력 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            final @AuthMember Member member,
            final @RequestBody MemberInfoRequest memberInfoRequest) {
        memberCommandFacade.registerMember(memberInfoRequest, member);

        return ResponseEntity.noContent().build();
    }
}
