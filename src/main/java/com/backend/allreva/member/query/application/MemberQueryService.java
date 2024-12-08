package com.backend.allreva.member.query.application;

import com.backend.allreva.member.query.application.response.MemberDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberDetailRepository memberDetailRepository;

    public MemberDetailResponse getById(final Long id) {
        return memberDetailRepository.findById(id);
    }
}
