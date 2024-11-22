package com.backend.allreva.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.allreva.member.command.application.MemberCommandService;
import com.backend.allreva.member.command.application.dto.MemberOAuth2RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuth2RegisterController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody MemberOAuth2RegisterRequest memberOAuth2RegisterRequest) {
        memberCommandService.registerOAuth2Member(memberOAuth2RegisterRequest);

        return ResponseEntity.noContent().build();
    }
}
