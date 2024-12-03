package com.backend.allreva.artist.query.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpotifySearchResponse(
        String id,
        String name
) {}