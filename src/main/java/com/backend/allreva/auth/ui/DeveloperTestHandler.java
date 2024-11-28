package com.backend.allreva.auth.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Profile("local")
public class DeveloperTestHandler {

    private final MemberRepository memberRepository;

    @Operation(summary = "전지전능한 개발자 모드 생성", description = "당신은 해당 API를 호출함으로써 초사이언 개발자 모드를 얻을 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/test-developer")
    public ResponseEntity<Response<String>> getAdminToken() {
        Optional<Member> optionalDeveloper = memberRepository.findMemberByMemberRole(MemberRole.DEVELOPER);
        if (optionalDeveloper.isEmpty()) {
            log.info("GOD saved");
            saveDeveloper();
        }
        return ResponseEntity.ok(Response.onSuccess("당신은 신이 되었습니다."));
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
