package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandService;
import com.backend.allreva.member.command.application.dto.MemberInfoUpdateRequest;
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
public class OAuth2RegisterController {

    private final MemberCommandService memberCommandService;

    @Operation(summary = "oauth2 회원가입", description = "oauth2 회원가입 시 회원 정보 입력 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 가입했습니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @AuthMember Member member,
            @RequestBody MemberInfoUpdateRequest memberInfoUpdateRequest) {
        memberCommandService.registerMember(memberInfoUpdateRequest, member);

        return ResponseEntity.noContent().build();
    }
}
