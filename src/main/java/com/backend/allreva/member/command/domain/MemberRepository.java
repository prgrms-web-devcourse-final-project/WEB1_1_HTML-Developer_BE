package com.backend.allreva.member.command.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.allreva.common.model.Email;
import com.backend.allreva.member.command.domain.value.LoginProvider;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);

    Optional<Member> findByEmailAndLoginProvider(Email email, LoginProvider loginProvider);
}
