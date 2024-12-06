package com.backend.allreva.member.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.allreva.member.command.application.request.MemberArtistRequest;
import com.backend.allreva.member.command.application.request.MemberInfoRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.support.ApiTestSupport;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
class OAuth2RegisterUITest extends ApiTestSupport {

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
                memberArtistRequests
        );
        var uploadedImage = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        willDoNothing().given(memberCommandFacade).registerMember(any(MemberInfoRequest.class), any(Member.class), any(MultipartFile.class));

        // when
        var resultActions = mockMvc.perform(multipart(HttpMethod.POST, "/api/v1/oauth2/register")
                .file(uploadedImage)
                .part(new MockPart("memberInfoRequest", "application/json", objectMapper.writeValueAsBytes(memberInfoRequest)))
                .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
