package com.backend.allreva.member.command.application;

import com.backend.allreva.member.command.application.dto.MemberInfoRequest.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberArtist;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberArtistCommandService {

    private final MemberArtistRepository memberArtistRepository;

    /**
     * 관심 아티스트 업데이트
     * 프론트에서 중복이 발생하지 않게 전처리 과정 필요
     */
    @Transactional
    public void updateMemberArtist(
            List<MemberArtistRequest> memberArtistRequests,
            Member member
    ) {
        // TODO: 아티스트 Spotify API 검색
        // 전체 삭제
        memberArtistRepository.deleteAllByMemberId(member.getId());

        // 전체 추가
        // TODO: bulk insert 적용
        List<MemberArtist> memberArtists = memberArtistRequests.stream()
                .map(request -> request.toEntity(member.getId()))
                .toList();
        memberArtistRepository.saveAll(memberArtists);
    }
}
