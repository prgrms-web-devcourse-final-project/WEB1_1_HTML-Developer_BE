package com.backend.allreva.member.command.application;

import com.backend.allreva.member.command.application.dto.MemberInfoRequest;
import com.backend.allreva.member.command.application.dto.RefundAccountRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberInfoCommandService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입 시 유저정보 입력
     * 회원 성공적으로 가입 시 GUEST에서 USER로 Role 변경
     */
    @Transactional
    public Member registerMember(
            MemberInfoRequest memberInfoRequest,
            Member member
    ) {
        member.setMemberInfo(
                memberInfoRequest.nickname(),
                memberInfoRequest.introduce(),
                memberInfoRequest.profileImageUrl()
        );
        member.upgradeToUser();
        return memberRepository.save(member);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public Member updateMemberInfo(
            MemberInfoRequest memberInfoRequest,
            Member member
    ) {
        member.setMemberInfo(
                memberInfoRequest.nickname(),
                memberInfoRequest.introduce(),
                memberInfoRequest.profileImageUrl()
        );
        return memberRepository.save(member);
    }

    /**
     * 환불 계좌 등록
     */
    public Member registerRefundAccount(
            RefundAccountRequest refundAccountRequest,
            Member member
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
        Member member
    ) {
        member.setDefaultRefundAccount();
        return memberRepository.save(member);
    }
}
