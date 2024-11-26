package com.backend.allreva.member.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberArtistRepository extends JpaRepository<MemberArtist, Long> {
    void deleteAllByMemberId(Long memberId);
}
