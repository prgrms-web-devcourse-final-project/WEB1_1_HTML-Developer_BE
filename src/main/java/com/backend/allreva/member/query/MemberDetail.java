package com.backend.allreva.member.query;

import com.backend.allreva.member.command.domain.value.RefundAccount;
import java.util.List;

public record MemberDetail(
        String email,
        String nickname,
        String introduce,
        String profileImageUrl,
        List<MemberArtistDetail> artists,
        String bank,
        String number
) {

    public MemberDetail(
            String email,
            String nickname,
            String introduce,
            String profileImageUrl,
            List<MemberArtistDetail> artists,
            RefundAccount refundAccount
    ) {
        this(email, nickname, introduce, profileImageUrl, artists,
                refundAccount.getBank(), refundAccount.getNumber());
    }

    public record MemberArtistDetail(
        String name,
        String artistId
    ) {

    }
}
