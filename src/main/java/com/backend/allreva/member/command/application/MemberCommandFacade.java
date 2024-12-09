package com.backend.allreva.member.command.application;

import com.backend.allreva.common.application.S3ImageService;
import com.backend.allreva.common.model.Image;
import com.backend.allreva.member.command.application.request.MemberInfoRequest;
import com.backend.allreva.member.command.application.request.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class MemberCommandFacade {

    private final MemberInfoCommandService memberInfoCommandService;
    private final MemberArtistCommandService memberArtistCommandService;
    private final S3ImageService s3ImageService;

    @Transactional
    public void registerMember(
            final MemberInfoRequest memberInfoRequest,
            final Member member,
            final MultipartFile image
    ) {
        Image uploadedImage = s3ImageService.upload(image);
        memberInfoCommandService.registerMember(memberInfoRequest, member, uploadedImage);
        memberArtistCommandService.updateMemberArtist(memberInfoRequest.memberArtistRequests(), member);
    }

    @Transactional
    public void updateMemberInfo(
            final MemberInfoRequest memberInfoRequest,
            final Member member,
            final MultipartFile image
    ) {
        Image uploadedImage = s3ImageService.upload(image);
        memberInfoCommandService.updateMemberInfo(memberInfoRequest, member, uploadedImage);
        memberArtistCommandService.updateMemberArtist(memberInfoRequest.memberArtistRequests(), member);
    }

    @Transactional
    public void registerRefundAccount(
            final RefundAccountRequest refundAccountRequest,
            final Member member
    ) {
        memberInfoCommandService.registerRefundAccount(refundAccountRequest, member);
    }

    @Transactional
    public void deleteRefundAccount(
            final Member member
    ) {
        memberInfoCommandService.deleteRefundAccount(member);
    }
}
