package com.backend.allreva.member.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandService;
import com.backend.allreva.member.command.application.dto.MemberInfoUpdateRequest;
import com.backend.allreva.member.command.domain.Member;
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

    @PatchMapping
    public ResponseEntity<Void> updateMemberInfo(
            @AuthMember Member member,
            @RequestBody MemberInfoUpdateRequest memberInfoUpdateRequest
    ) {
        memberCommandService.updateMemberInfo(memberInfoUpdateRequest, member);

        return ResponseEntity.noContent().build();
    }
}
