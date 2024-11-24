package com.backend.allreva.member.command.application;

import com.backend.allreva.member.command.application.dto.RefundAccountRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.allreva.member.command.application.dto.MemberInfoUpdateRequest;
import com.backend.allreva.member.command.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입 시 유저정보 입력
     * 회원 성공적으로 가입 시 GUEST에서 USER로 Role 변경
     */
    @Transactional
    public Member registerMember(
            MemberInfoUpdateRequest memberInfoUpdateRequest,
            Member member
    ) {
        member.setMemberInfo(
                memberInfoUpdateRequest.nickname(),
                memberInfoUpdateRequest.introduce(),
                memberInfoUpdateRequest.profileImageUrl()
        );
        return member;
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public Member updateMemberInfo(
            MemberInfoUpdateRequest memberInfoUpdateRequest,
            Member member
    ) {
        member.setMemberInfo(
                memberInfoUpdateRequest.nickname(),
                memberInfoUpdateRequest.introduce(),
                memberInfoUpdateRequest.profileImageUrl()
        );
        member.upgradeToUser();
        return member;
    }

    /**
     * 환불 계좌 등록
     */
    public Member registerRefundAccount(
            RefundAccountRegisterRequest refundAccountRegisterRequest,
            Member member
    ) {
        member.setRefundAccount(
                refundAccountRegisterRequest.bank(),
                refundAccountRegisterRequest.number()
        );
        return member;
    }

    /**
     * 환불 계좌 수정
     */
    public Member updateRefundAccount(
            RefundAccountRegisterRequest refundAccountRegisterRequest,
            Member member
    ) {
        member.setRefundAccount(
                refundAccountRegisterRequest.bank(),
                refundAccountRegisterRequest.number()
        );
        return member;
    }

    /**
     * 환불 계좌 soft delete
     */
    public void deleteRefundAccount(
        Member member
    ) {
        memberRepository.delete(member);
    }
}
