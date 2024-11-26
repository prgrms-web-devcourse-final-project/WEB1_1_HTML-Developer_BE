package com.backend.allreva.member.command.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberArtistRepository extends JpaRepository<MemberArtist, Long> {
    List<MemberArtist> findByMemberId(Long memberId);
}
