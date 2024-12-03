package com.backend.allreva.member.ui;

import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.support.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class OAuth2RegisterUITest extends ApiTestSupport {

    @Test
    void oauth2_회원가입_API() throws Exception {
        // given
        var memberArtistRequests = List.of(
                new MemberArtistRequest("spotify_1L","name1"),
                new MemberArtistRequest("spotify_2L", "name2")
        );
        var memberInfoRequest = new MemberInfoRequest(
                "updated nickname",
                "test introduce",
                "updated profile image url",
                memberArtistRequests
        );
        willDoNothing().given(memberCommandFacade).registerMember(any(MemberInfoRequest.class), any(Member.class));

        // when
        var resultActions = mockMvc.perform(post("/api/v1/oauth2/register")
                .content(objectMapper.writeValueAsString(memberInfoRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
