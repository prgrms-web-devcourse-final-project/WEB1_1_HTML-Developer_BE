package com.backend.allreva.member.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.member.command.application.MemberArtistCommandService;
import com.backend.allreva.member.command.application.MemberArtistRepository;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberArtistCommandTest {

    @InjectMocks
    private MemberArtistCommandService memberArtistCommandService;

    @Mock
    private MemberArtistRepository memberArtistRepository;

    Member member;

    @BeforeEach
    void setUp() {
        member = Member.createTemporary(
                "my@email",
                "nickname",
                LoginProvider.GOOGLE,
                "https://my_picture");
        ReflectionTestUtils.setField(member, "id", 1L);
    }

    @Test
    void 관심_아티스트를_성공적으로_수정한다() {
        // given
        List<MemberArtistRequest> requests = List.of(
                new MemberArtistRequest("spotify_1L"),
                new MemberArtistRequest("spotify_2L")
        );

        // when
        memberArtistCommandService.updateMemberArtist(requests, member);

        // then
        verify(memberArtistRepository, times(1)).deleteAllByMemberId(any(Long.class));
        verify(memberArtistRepository, times(1)).saveAll(any());
    }
}
