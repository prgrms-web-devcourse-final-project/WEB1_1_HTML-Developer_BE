package com.backend.allreva.member.query.application.response;

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

    // for querydsl projections
    public MemberDetail(
            final String email,
            final String nickname,
            final String introduce,
            final String profileImageUrl,
            final List<MemberArtistDetail> artists,
            final RefundAccount refundAccount
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
