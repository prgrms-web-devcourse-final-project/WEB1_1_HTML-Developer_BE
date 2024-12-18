package com.backend.allreva.auth.application;

import com.backend.allreva.auth.application.dto.LoginResponse;
import com.backend.allreva.auth.application.dto.ReissueRequest;
import com.backend.allreva.auth.application.dto.ReissueResponse;
import com.backend.allreva.auth.application.dto.UserInfo;
import com.backend.allreva.auth.exception.code.InvalidJwtTokenException;
import com.backend.allreva.auth.exception.code.TokenNotFoundException;
import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import java.util.Optional;
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

    /**
     * 카카오 로그인을 검증합니다.
     * @param authorizationCode 인가 코드
     * @return 로그인 응답
     */
    public LoginResponse kakaoLogin(final String authorizationCode) {
        UserInfo userInfo = oAuth2LoginService.getUserInfo(authorizationCode);

        // 회원 존재 확인
        Email emailVO = Email.builder()
                .email(userInfo.email())
                .build();
        LoginProvider loginProvider = userInfo.loginProvider();
        Optional<Member> memberOptional = memberRepository.findByEmailAndLoginProvider(emailVO, loginProvider);

        if (memberOptional.isPresent()) {
            return getMemberInfo(memberOptional.get());
        }
        return getTemporaryMemberInfo(userInfo);

    }

    private LoginResponse getTemporaryMemberInfo(final UserInfo userInfo) {
        return LoginResponse.builder()
                .isUser(false)
                .email(userInfo.email())
                .nickname(userInfo.nickname())
                .profileImageUrl(userInfo.profileImageUrl())
                .build();
    }

    private LoginResponse getMemberInfo(final Member member) {
        // token 생성
        Long memberId = member.getId();
        String accessToken = jwtService.generateAccessToken(String.valueOf(memberId));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(memberId));

        // redis에 RefreshToken 저장
        jwtService.updateRefreshToken(refreshToken, memberId);

        return LoginResponse.builder()
                .isUser(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(member.getEmail().getEmail())
                .nickname(member.getMemberInfo().getNickname())
                .profileImageUrl(member.getMemberInfo().getProfileImageUrl())
                .build();
    }

    /**
     * Access Token을 재발급합니다.
     * @param reissueRequest Refresh Token
     * @Return 재발급된 Access Token 및 Refresh Token
     */
    public ReissueResponse reissueAccessToken(final ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.refreshToken();

        // refresh token 검증
        boolean isRefreshTokenValid = jwtService.validateToken(refreshToken);
        if (!isRefreshTokenValid) {
            throw new InvalidJwtTokenException();
        }
        if (!jwtService.isRefreshTokenExist(refreshToken)) {
            throw new TokenNotFoundException();
        }

        // access token 재발급
        String memberId = jwtService.extractMemberId(refreshToken);
        String generatedAccessToken = jwtService.generateAccessToken(memberId);

        // token rotate
        String generateRefreshToken = jwtService.generateRefreshToken(memberId);
        jwtService.updateRefreshToken(generateRefreshToken, Long.valueOf(memberId));

        return ReissueResponse.builder()
                .accessToken(generatedAccessToken)
                .refreshToken(generateRefreshToken)
                .build();
    }
}
