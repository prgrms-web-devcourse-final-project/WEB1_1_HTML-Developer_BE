package com.backend.allreva.member.command.application;

import com.backend.allreva.artist.command.ArtistCommandService;
import com.backend.allreva.artist.query.application.ArtistQueryService;
import com.backend.allreva.member.command.application.dto.MemberArtistRequest;
import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.member.command.domain.MemberArtist;
import com.backend.allreva.member.command.domain.MemberArtistRepository;
import com.backend.allreva.member.command.domain.MemberArtistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberArtistCommandService {

    private final MemberArtistRepository memberArtistRepository;
    private final MemberArtistService memberArtistService;
    private final ArtistCommandService artistCommandService;
    private final ArtistQueryService artistQueryService;
    /**
     * 관심 아티스트 업데이트
     */
    public void updateMemberArtist(
            final List<MemberArtistRequest> memberArtistRequests,
            final Member member
    ) {
        artistCommandService.saveIfNotExist(memberArtistRequests);
        List<MemberArtist> preMemberArtists = memberArtistRepository.findByMemberId(member.getId());

        // MemberArtist 추가
        // TODO: bulk insert 적용
        List<MemberArtist> addMemberArtists = memberArtistRequests.stream()
                .filter(req -> memberArtistService.isNewMemberArtists(req, preMemberArtists))
                .map(req -> artistQueryService.getArtistById(req.spotifyArtistId()))
                .map(artist -> MemberArtist.builder()
                        .memberId(member.getId())
                        .artistId(artist.getId())
                        .build())
                .toList();
        memberArtistRepository.saveAll(addMemberArtists);

        // MemberArtist 삭제
        // TODO: bulk delete 적용, bulk 연산 적용 시 soft delete 안되는 문제 고민
        List<MemberArtist> removeMemberArtists = preMemberArtists.stream()
                .filter(pre -> memberArtistService.isRemoveMemberArtist(pre, memberArtistRequests))
                .toList();
        memberArtistRepository.deleteAll(removeMemberArtists);
    }
}
