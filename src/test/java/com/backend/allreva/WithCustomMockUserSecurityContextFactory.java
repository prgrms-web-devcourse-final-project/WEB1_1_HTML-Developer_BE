package com.backend.allreva;

import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {


        Member member = Member.createTemporary("allreva@gmail.com", "닉네임", LoginProvider.KAKAO, ".jpg");
        ReflectionTestUtils.setField(member, "id", 1L);
        member.updateMemberInfo("닉네임", "소개", ".jpg");

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        PrincipalDetails principalDetails = new PrincipalDetails(member, null);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);

        return context;
    }
}
