package com.backend.allreva.artist.command;

import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.artist.query.application.dto.SpotifySearchResponse;
import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.support.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
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
        //given
        String query = "day6";
        List<SpotifySearchResponse> responses = artistQueryService.searchArtist(query);
        List<MemberArtistRequest> memberArtistRequests = responses.stream()
                .map(response -> {
                    return new MemberArtistRequest(response.id(), response.name());
                }).toList();

        //when
        artistCommandService.saveIfNotExist(memberArtistRequests);

        //then
        memberArtistRequests.forEach(memberArtistRequest -> {
            assertThat(
                    artistQueryService.getArtistById(memberArtistRequest.spotifyArtistId())
            ).isNotNull();
        });
    }

}