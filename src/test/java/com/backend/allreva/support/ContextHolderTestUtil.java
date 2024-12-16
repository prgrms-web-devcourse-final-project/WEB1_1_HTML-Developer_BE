package com.backend.allreva.support;

import com.backend.allreva.auth.security.PrincipalDetails;
import com.backend.allreva.member.command.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public final class ContextHolderTestUtil {

    public static void setContextHolder(Member member) {
        var principalDetails = new PrincipalDetails(member);
        var authentication = new UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void cleanContextHolder() {
        SecurityContextHolder.clearContext();
    }
}
