package com.backend.allreva.member.ui;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuth2Controller {

    private final MemberCommandFacade memberCommandFacade;

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
