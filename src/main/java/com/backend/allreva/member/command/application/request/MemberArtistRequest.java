package com.backend.allreva.member.command.application.request;

import com.backend.allreva.artist.command.domain.Artist;

public record MemberArtistRequest(
        String spotifyArtistId,
        String name
) {
    public static Artist to(final MemberArtistRequest memberArtistRequest) {
        return new Artist(memberArtistRequest.spotifyArtistId(), memberArtistRequest.name());
    }
}
