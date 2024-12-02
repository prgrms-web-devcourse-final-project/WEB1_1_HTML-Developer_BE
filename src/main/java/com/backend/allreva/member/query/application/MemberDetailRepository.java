package com.backend.allreva.member.query.application;

import com.backend.allreva.member.query.application.dto.MemberDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailRepository {
    MemberDetail findById(final Long id);
}
