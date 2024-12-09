package com.backend.allreva.artist.integration;

import com.backend.allreva.artist.exception.ArtistSearchNoContentException;
import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.artist.query.application.response.SpotifySearchResponse;
import com.backend.allreva.support.IntegrationTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    ArtistQueryService artistQueryService;

    @DisplayName("아티스트 검색에 성공한다.")
    @Test
    void searchArtistTest() {
        //given
        String query = "하현상";

        //when
        List<SpotifySearchResponse> responses = artistQueryService.searchArtist(query);

        //then
        assertThat(responses).isNotEmpty();
    }

    @DisplayName("아티스트 검색 결과가 없을 시 예외 반환")
    @Test
    void searchArtistExceptionTest() {
        //given
        String query = "데이삭스";

        //when
        //then
        Assertions.assertThrows(ArtistSearchNoContentException.class,
                () -> artistQueryService.searchArtist(query)
        );
    }
}