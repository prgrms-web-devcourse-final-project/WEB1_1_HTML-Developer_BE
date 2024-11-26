package com.backend.allreva.member.query.application;

import com.backend.allreva.member.query.MemberDetail;
import com.backend.allreva.member.query.MemberDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberDetailRepository memberDetailRepository;

    public MemberDetail getById(Long id) {
        return memberDetailRepository.findById(id);
    }
}
