package com.backend.allreva.common.feign;

import com.backend.allreva.artist.query.application.dto.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spotifyAccount", url = "https://accounts.spotify.com")
public interface SpotifyAccountClient {
    @PostMapping(value = "/api/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    SpotifyTokenResponse getAccessToken(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType
    );
}
