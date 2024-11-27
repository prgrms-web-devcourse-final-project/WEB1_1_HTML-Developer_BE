package com.backend.allreva.common.runner;

import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDummyDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        Member admin = Member.builder()
                .email(Email.builder().email("admin@email.com").build())
                .nickname("admin")
                .loginProvider(LoginProvider.GOOGLE)
                .memberRole(MemberRole.ADMIN)
                .introduce("test")
                .profileImageUrl("https://my_picture")
                .build();
        memberRepository.save(admin);

        Member user = Member.builder()
                .email(Email.builder().email("user@email.com").build())
                .nickname("user")
                .loginProvider(LoginProvider.GOOGLE)
                .memberRole(MemberRole.USER)
                .introduce("test")
                .profileImageUrl("https://my_picture")
                .build();
        memberRepository.save(user);

        Member guest = Member.createTemporary(
                "guest@email.com",
                "guest",
                LoginProvider.GOOGLE,
                "https://my_picture");
        memberRepository.save(guest);
    }
}
