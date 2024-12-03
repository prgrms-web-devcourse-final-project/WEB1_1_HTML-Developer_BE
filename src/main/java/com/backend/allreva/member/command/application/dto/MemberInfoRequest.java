package com.backend.allreva.member.command.application.dto;

import com.backend.allreva.artist.command.domain.Artist;

import java.util.List;

public record MemberInfoRequest(
        String nickname,
        String introduce,
        String profileImageUrl,
        List<MemberArtistRequest> memberArtistRequests
) {

    public record MemberArtistRequest(
            String spotifyArtistId,
            String name
    ) {
        public static Artist to(final MemberArtistRequest memberArtistRequest) {
            return new Artist(memberArtistRequest.spotifyArtistId(), memberArtistRequest.name());
        }
    }
}
