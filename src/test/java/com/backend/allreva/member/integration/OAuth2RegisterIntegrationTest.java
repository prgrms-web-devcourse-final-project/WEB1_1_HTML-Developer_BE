package com.backend.allreva.member.integration;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.allreva.artist.command.ArtistRepository;
import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.member.fixture.MemberFixture;
import com.backend.allreva.support.ContextHolderTestUtil;
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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class OAuth2RegisterIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MemberRepository memberRepository;

    protected Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMemberFixture(1L, MemberRole.GUEST);
        ContextHolderTestUtil.setContextHolder(member);
    }

    @AfterEach
    void cleanUp() {
        ContextHolderTestUtil.cleanContextHolder();
    }

    @Test
    @Transactional
    void oauth2_회원가입_API_통합_테스트() throws Exception {
        // given
        var artist = Artist.builder()
                .id("spotify_1L")
                .name("Spotify").build();
        artistRepository.save(artist);

        var memberInfoRequest = new MemberInfoRequest(
                "updated nickname",
                "test introduce",
                "updated profile image url",
                List.of(new MemberArtistRequest("spotify_1L"))
        );

        // when
        var resultActions = mockMvc.perform(post("/api/v1/oauth2/register")
                .content(objectMapper.writeValueAsString(memberInfoRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Member> registeredMember = memberRepository.findById(member.getId());
        registeredMember.ifPresent(registered ->
                assertSoftly(softly -> {
                    softly.assertThat(registered.getMemberRole()).isEqualTo(MemberRole.USER);
                    softly.assertThat(registered.getMemberInfo().getIntroduce()).isEqualTo("test introduce");
                }));
    }
}
