package com.backend.allreva.artist.query.application.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotifySearchResponse(
        String id,
        String name,
        List<Image> images
) {}