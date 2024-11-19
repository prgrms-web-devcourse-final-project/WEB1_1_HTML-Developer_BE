package com.kwanse.allreva.member.command.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwanse.allreva.member.command.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
