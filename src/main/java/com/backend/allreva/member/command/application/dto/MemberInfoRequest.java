package com.backend.allreva.member.command.application.dto;

import com.backend.allreva.member.command.domain.MemberArtist;
import java.util.List;

public record MemberInfoRequest(
        String nickname,
        String introduce,
        String profileImageUrl,
        List<MemberArtistRequest> memberArtistRequests
) {

    public record MemberArtistRequest(
            String spotifyArtistId
    ) {
        public MemberArtist toEntity(Long memberId) {
            return MemberArtist.builder()
                    .memberId(memberId)
                    .artistId(spotifyArtistId)
                    .build();
        }
    }
}
