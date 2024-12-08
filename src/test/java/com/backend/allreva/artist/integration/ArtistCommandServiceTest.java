package com.backend.allreva.artist.integration;

import com.backend.allreva.artist.command.application.ArtistCommandService;
import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.artist.query.application.response.SpotifySearchResponse;
import com.backend.allreva.member.command.application.request.MemberArtistRequest;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistCommandServiceTest extends IntegrationTestSupport {
    @Autowired
    ArtistCommandService artistCommandService;

    @Autowired
    ArtistQueryService artistQueryService;

    @DisplayName("아티스트 검색후 값이 존재하지 않을 경우 DB에 삽입")
    @Test
    void saveIfNotExistTest() {
        // given
        String query = "day6";
        List<SpotifySearchResponse> responses = artistQueryService.searchArtist(query);
        List<MemberArtistRequest> memberArtistRequests = responses.stream()
                .map(response -> new MemberArtistRequest(response.id(), response.name()))
                .toList();

        // when
        artistCommandService.saveIfNotExist(memberArtistRequests);

        // then
        for (MemberArtistRequest request : memberArtistRequests) {
            Artist savedArtist = artistQueryService.getArtistById(request.spotifyArtistId());
            assertThat(savedArtist)
                    .isNotNull()
                    .satisfies(artist -> {
                        assertThat(artist.getName()).isEqualTo(request.name());
                        assertThat(artist.getId()).isEqualTo(request.spotifyArtistId());
                    });
        }
    }

}