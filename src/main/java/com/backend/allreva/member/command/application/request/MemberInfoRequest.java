package com.backend.allreva.member.command.application.request;

import java.util.List;

public record MemberInfoRequest(
        String nickname,
        String introduce,
        List<MemberArtistRequest> memberArtistRequests
) {

}
