package com.backend.allreva.member.query;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailRepository {
    MemberDetail findById(Long id);
}
