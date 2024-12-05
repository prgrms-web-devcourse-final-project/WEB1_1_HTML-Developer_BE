package com.backend.allreva.member.command.domain;

import com.backend.allreva.member.command.application.dto.MemberArtistRequest;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemberArtistService {

    // 새로운 memberArtist 정보가 이전에 있던 memberArtist들과 다른지
    public boolean isNewMemberArtists(final MemberArtistRequest req, final List<MemberArtist> preMemberArtists) {
        return preMemberArtists.stream()
                .noneMatch(pre -> pre.getArtistId().equals(req.spotifyArtistId()));
    }

    // 기존에 있던 memberArtist들이 새로운 memberArtist들에게 속해있지 않은지
    public boolean isRemoveMemberArtist(final MemberArtist pre, final List<MemberArtistRequest> memberArtistRequests) {
        return memberArtistRequests.stream()
                .noneMatch(req -> req.spotifyArtistId().equals(pre.getArtistId()));
    }
}
