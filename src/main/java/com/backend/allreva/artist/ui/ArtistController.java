package com.backend.allreva.artist.ui;

import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.artist.query.application.dto.SpotifySearchResponse;
import com.backend.allreva.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistQueryService artistQueryService;

    @GetMapping("/search")
    public Response<List<SpotifySearchResponse>> searchArtist(
            final @RequestParam String query) {
        return Response.onSuccess(
                artistQueryService.searchArtist(query)
        );
    }

}
