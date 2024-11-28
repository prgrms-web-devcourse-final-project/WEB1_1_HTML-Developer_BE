package com.backend.allreva.member.command.domain;

import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.value.LoginProvider;
import com.backend.allreva.member.command.domain.value.MemberRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberRole(final MemberRole memberRole);

    Optional<Member> findByEmailAndLoginProvider(final Email email, final LoginProvider loginProvider);
}
