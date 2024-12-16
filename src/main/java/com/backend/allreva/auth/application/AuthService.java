package com.backend.allreva.auth.application;

import com.backend.allreva.auth.application.dto.LoginResponse;
import com.backend.allreva.auth.application.dto.UserInfo;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final OAuth2LoginService oAuth2LoginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public String getLoginURL() {
        return oAuth2LoginService.getRequestURL();
    }

    public LoginResponse kakaoLogin(String authorizationCode) {
        UserInfo userInfo = oAuth2LoginService.getUserInfo(authorizationCode);

        // 회원 존재 확인, 없다면 임시 회원 정보 생성
        Email emailVO = Email.builder()
                .email(userInfo.email())
                .build();
        LoginProvider loginProvider = userInfo.loginProvider();
        Member member = memberRepository.findByEmailAndLoginProvider(emailVO, loginProvider)
                .orElseGet(() -> registerTemporaryMember(userInfo));

        // token 생성
        Long memberId = member.getId();
        String accessToken = jwtService.generateAccessToken(String.valueOf(memberId));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(memberId));

        // redis에 RefreshToken 저장
        jwtService.updateRefreshToken(refreshToken, memberId);

        // 결과 값 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(userInfo.email())
                .profileImageUrl(userInfo.profileImageUrl())
                .build();
    }

    private Member registerTemporaryMember(final UserInfo userInfo) {
        Member temporaryMember = Member.createTemporary(
                userInfo.email(),
                userInfo.nickname(),
                userInfo.loginProvider(),
                userInfo.profileImageUrl());
        return memberRepository.save(temporaryMember);
    }
}
