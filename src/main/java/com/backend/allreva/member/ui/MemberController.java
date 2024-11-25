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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 수정했습니다.", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping
    public ResponseEntity<Void> updateMemberInfo(
            @AuthMember Member member,
            @RequestBody MemberInfoUpdateRequest memberInfoUpdateRequest
    ) {
        memberCommandService.updateMemberInfo(memberInfoUpdateRequest, member);

        return ResponseEntity.noContent().build();
    }
}
