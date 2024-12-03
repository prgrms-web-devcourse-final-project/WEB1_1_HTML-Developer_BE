package com.backend.allreva.artist.query.application.dto;

public record SpotifyTokenResponse(
        String access_token,
        String token_type,
        int expires_in
) {
}
