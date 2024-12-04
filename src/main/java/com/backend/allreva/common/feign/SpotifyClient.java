package com.backend.allreva.common.feign;

import com.backend.allreva.artist.query.application.dto.SpotifyArtistWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spotify", url = "https://api.spotify.com")
public interface SpotifyClient {
    @GetMapping(value = "/v1/search", params = "type=artist")
    SpotifyArtistWrapper searchArtists(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("q") String query
    );
}