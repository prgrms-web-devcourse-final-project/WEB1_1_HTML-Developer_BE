package com.backend.allreva.artist.query.application.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Image(
        String url,
        int height,
        int width
) {
}
