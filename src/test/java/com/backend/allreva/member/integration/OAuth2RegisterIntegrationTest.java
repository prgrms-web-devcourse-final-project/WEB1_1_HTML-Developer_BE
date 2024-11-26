package com.backend.allreva.member.integration;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.allreva.artist.command.ArtistRepository;
import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.auth.application.dto.PrincipalDetails;
import com.backend.allreva.member.command.application.MemberRepository;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.support.DatabaseCleanUpUtil;
import com.backend.allreva.support.IntegrationTestSupport;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class OAuth2RegisterIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MemberRepository memberRepository;

    protected Member member;

    @BeforeEach
    void setUp() {
        member = Member.createTemporary(
                "my@email",
                "nickname",
                LoginProvider.GOOGLE,
                "https://my_picture");
        ReflectionTestUtils.setField(member, "id", 1L);

        PrincipalDetails principalDetails = new PrincipalDetails(member, null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
                principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void cleanUp() {
        entityManager.createNativeQuery(DatabaseCleanUpUtil.getDeleteSql("artist")).executeUpdate();
        SecurityContextHolder.clearContext();
    }

    @Test
    @Transactional
    void oauth2_회원가입_API_통합_테스트() throws Exception {
        // given
        Artist artist = Artist.builder()
                .id("spotify_1L")
                .name("Spotify").build();
        artistRepository.save(artist);

        MemberInfoRequest memberInfoRequest = new MemberInfoRequest(
                "updated nickname",
                "test introduce",
                "updated profile image url",
                List.of(new MemberArtistRequest("spotify_1L"))
        );

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/oauth2/register")
                .content(objectMapper.writeValueAsString(memberInfoRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
        Optional<Member> registeredMember = memberRepository.findById(member.getId());
        registeredMember.ifPresent(registered ->
                assertSoftly(softly -> {
                    softly.assertThat(registered.getMemberRole()).isEqualTo(MemberRole.USER);
                    softly.assertThat(registered.getMemberInfo().getIntroduce()).isEqualTo("test introduce");
                }));
    }
}
