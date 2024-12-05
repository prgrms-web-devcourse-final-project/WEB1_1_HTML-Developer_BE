package com.backend.allreva.auth.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.member.command.application.MemberCommandFacade;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuth2Controller implements OAuth2ControllerSwagger{

    private final MemberCommandFacade memberCommandFacade;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        return null;
    }

    /**
     * oauth2 회원가입
     */
    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerMember(
            @AuthMember final Member member,
            @RequestPart final MemberInfoRequest memberInfoRequest,
            @RequestPart(value = "image", required = false) final MultipartFile image
    ) {
        memberCommandFacade.registerMember(memberInfoRequest, member, image);
        return ResponseEntity.noContent().build();
    }
}
