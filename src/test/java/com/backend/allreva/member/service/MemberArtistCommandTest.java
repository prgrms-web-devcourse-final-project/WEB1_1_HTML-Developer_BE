package com.backend.allreva.member.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.artist.command.application.ArtistCommandService;
import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.member.command.application.MemberArtistCommandService;
import com.backend.allreva.member.command.application.request.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberArtistRepository;
import com.backend.allreva.member.command.domain.MemberArtistService;
import com.backend.allreva.member.command.domain.value.MemberRole;
import com.backend.allreva.member.fixture.MemberFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberArtistCommandTest {

    @Mock
    private MemberArtistRepository memberArtistRepository;

    @Mock
    private ArtistQueryService artistQueryService;

    @Mock
    private ArtistCommandService artistCommandService;

    @InjectMocks
    private MemberArtistCommandService memberArtistCommandService;

    @Mock
    private MemberArtistService memberArtistService;

    Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMemberFixture(1L, MemberRole.USER);
    }

    @Test
    void 관심_아티스트를_성공적으로_수정한다() {
        // given
        var artist = Artist.builder()
                .id("spotify_1L")
                .name("하현상")
                .build();
        given(artistQueryService.getArtistById(any(String.class))).willReturn(artist);
        given(memberArtistService.isNewMemberArtists(any(), any())).willReturn(true);
        var memberArtistRequests = List.of(new MemberArtistRequest("spotify_1L","name1"));

        // when
        memberArtistCommandService.updateMemberArtist(memberArtistRequests, member);

        // then
        verify(memberArtistRepository, times(1)).deleteAll(any());
        verify(memberArtistRepository, times(1)).saveAll(any());
        verify(artistCommandService, times(1)).saveIfNotExist(any());
    }
}
