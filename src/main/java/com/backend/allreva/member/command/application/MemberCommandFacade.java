package com.backend.allreva.member.command.application;

import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberCommandFacade {

    private final MemberInfoCommandService memberInfoCommandService;
    private final MemberArtistCommandService memberArtistCommandService;

    @Transactional
    public void registerMember(
            MemberInfoRequest memberInfoRequest,
            Member member
    ) {
        // TODO: 이미지 S3 등록 및 URL 응답
        memberInfoCommandService.registerMember(memberInfoRequest, member);
        memberArtistCommandService.updateMemberArtist(memberInfoRequest.memberArtistRequests(), member);
    }

    @Transactional
    public void updateMemberInfo(
            MemberInfoRequest memberInfoRequest,
            Member member
    ) {
        memberInfoCommandService.updateMemberInfo(memberInfoRequest, member);
        memberArtistCommandService.updateMemberArtist(memberInfoRequest.memberArtistRequests(), member);
    }

    @Transactional
    public void registerRefundAccount(
            RefundAccountRequest refundAccountRequest,
            Member member
    ) {
        memberInfoCommandService.registerRefundAccount(refundAccountRequest, member);
    }

    @Transactional
    public void deleteRefundAccount(
            Member member
    ) {
        memberInfoCommandService.deleteRefundAccount(member);
    }
}
