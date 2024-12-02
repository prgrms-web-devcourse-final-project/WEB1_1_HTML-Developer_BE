package com.backend.allreva.auth.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Profile("local")
public class DeveloperTestHandler implements DeveloperTestHandlerSwagger {

    private final MemberRepository memberRepository;

    @GetMapping("/test-developer")
    public Response<String> getAdminToken() {
        Optional<Member> optionalDeveloper = memberRepository.findMemberByMemberRole(MemberRole.DEVELOPER);
        if (optionalDeveloper.isEmpty()) {
            log.info("GOD saved");
            saveDeveloper();
        }
        return Response.onSuccess("당신은 신이 되었습니다.");
    }

    private void saveDeveloper() {
        Member developer = Member.builder()
                .memberRole(MemberRole.DEVELOPER)
                .email(Email.builder().email("developer@developer.com").build())
                .nickname("developer")
                .loginProvider(LoginProvider.ORIGINAL)
                .profileImageUrl("developer")
                .build();
        memberRepository.save(developer);
    }
}
