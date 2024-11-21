package com.backend.allreva.auth.oauth2.application;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.auth.oauth2.application.dto.OAuth2UserInfo;
import com.backend.allreva.auth.oauth2.exception.UnsupportedProviderException;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1 - OAuth2 Client를 사용하여 Auth Server로부터 유저 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2 - socialType 가져오기(third-party id)
        String socialType = userRequest.getClientRegistration().getRegistrationId();

        // 3 - 유저 정보 dto 생성(Auth Server로부터 받은 attributes들을 토대로 유저 정보 DTO를 만듬)
        OAuth2UserInfo oAuth2UserInfo = switch (socialType) {
            case "google" -> OAuth2UserInfo.ofGoogle(oAuth2User.getAttributes());
            case "kakao" -> OAuth2UserInfo.ofKakao(oAuth2User.getAttributes());
            default -> throw new UnsupportedProviderException(); // ISSUE: 해당 정보 가져오기 전에 이미 Filter단에서 미리 구현된 다른 예외가 잡힘
        };

        // 4 - 회원 존재 확인, 없다면 임시 회원 정보 생성
        Email emailVO = Email.builder()
                .email(oAuth2UserInfo.email())
                .build();
        Member member = memberRepository.findByEmail(emailVO)
                .orElseGet(() -> Member.createTemporary(
                        oAuth2UserInfo.email(),
                        oAuth2UserInfo.nickname(),
                        oAuth2UserInfo.loginProvider(),
                        oAuth2UserInfo.profile()));

        // 5 - OAuth2User로 반환
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
