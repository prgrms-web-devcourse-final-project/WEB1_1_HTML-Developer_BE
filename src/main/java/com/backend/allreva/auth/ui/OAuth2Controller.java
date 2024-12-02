package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
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
public class OAuth2Controller implements OAuth2ControllerSwagger{

    private final MemberCommandFacade memberCommandFacade;

    @Override
    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        return null;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            final @AuthMember Member member,
            final @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        memberCommandFacade.registerMember(memberInfoRequest, member);
        return ResponseEntity.noContent().build();
    }
}
