package com.backend.allreva.artist.query.application.response;

public record SpotifyTokenResponse(
        String access_token,
        String token_type,
        int expires_in
) {
}
