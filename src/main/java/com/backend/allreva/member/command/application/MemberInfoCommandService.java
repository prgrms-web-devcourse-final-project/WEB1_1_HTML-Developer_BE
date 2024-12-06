package com.backend.allreva.member.command.application;

import com.backend.allreva.common.model.Image;
import com.backend.allreva.member.command.application.request.MemberInfoRequest;
import com.backend.allreva.member.command.application.request.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoCommandService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입 시 유저정보 입력 회원 성공적으로 가입 시 GUEST에서 USER로 Role 변경
     */
    public Member registerMember(
            final MemberInfoRequest memberInfoRequest,
            final Member member,
            final Image image
    ) {
        member.setMemberInfo(
                memberInfoRequest.nickname(),
                memberInfoRequest.introduce(),
                image.getUrl()
        );
        member.upgradeToUser(); // USER 권한으로 변경
        return memberRepository.save(member);
    }

    /**
     * 회원 정보 수정
     */
    public Member updateMemberInfo(
            final MemberInfoRequest memberInfoRequest,
            final Member member,
            final Image image
    ) {
        member.setMemberInfo(
                memberInfoRequest.nickname(),
                memberInfoRequest.introduce(),
                image.getUrl()
        );
        return memberRepository.save(member);
    }

    /**
     * 환불 계좌 등록
     */
    public Member registerRefundAccount(
            final RefundAccountRequest refundAccountRequest,
            final Member member
    ) {
        member.setRefundAccount(
                refundAccountRequest.bank(),
                refundAccountRequest.number()
        );
        return memberRepository.save(member);
    }

    /**
     * 환불 계좌 soft delete
     */
    public Member deleteRefundAccount(
            final Member member
    ) {
        member.setDefaultRefundAccount();
        return memberRepository.save(member);
    }
}
