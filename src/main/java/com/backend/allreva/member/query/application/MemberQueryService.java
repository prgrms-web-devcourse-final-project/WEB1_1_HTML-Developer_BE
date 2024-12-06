package com.backend.allreva.member.query.application;

import com.backend.allreva.member.query.application.response.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberDetailRepository memberDetailRepository;

    public MemberDetail getById(final Long id) {
        return memberDetailRepository.findById(id);
    }
}
