package com.backend.allreva.member.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.allreva.member.command.application.dto.MemberOAuth2RegisterRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;

    @Transactional
    public void registerOAuth2Member(MemberOAuth2RegisterRequest memberOAuth2RegisterRequest) {
        Member member = memberRepository.findById(memberOAuth2RegisterRequest.memberId())
                .orElseThrow(() -> new MemberNotFoundException());

        member.updateMemberInfo(
                memberOAuth2RegisterRequest.nickname(),
                memberOAuth2RegisterRequest.introduce(),
                memberOAuth2RegisterRequest.profileImageUrl());
    }
}
