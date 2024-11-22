package com.backend.allreva.member.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.allreva.member.command.application.dto.MemberOAuth2RegisterRequest;
import com.backend.allreva.member.command.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    @Transactional
    public void registerOAuth2Member(
            MemberOAuth2RegisterRequest memberOAuth2RegisterRequest,
            Member member) {
        member.updateMemberInfo(
                memberOAuth2RegisterRequest.nickname(),
                memberOAuth2RegisterRequest.introduce(),
                memberOAuth2RegisterRequest.profileImageUrl());
    }
}
