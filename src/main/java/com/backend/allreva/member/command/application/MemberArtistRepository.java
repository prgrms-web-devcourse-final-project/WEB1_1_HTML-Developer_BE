package com.backend.allreva.member.command.application;

import com.backend.allreva.member.command.domain.MemberArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberArtistRepository extends JpaRepository<MemberArtist, Long> {
    void deleteAllByMemberId(Long memberId);
}
