package com.backend.allreva.member.infra;

import static com.backend.allreva.artist.command.domain.QArtist.artist;
import static com.backend.allreva.member.command.domain.QMember.member;
import static com.backend.allreva.member.command.domain.QMemberArtist.memberArtist;

import com.backend.allreva.member.query.application.MemberDetailRepository;
import com.backend.allreva.member.query.application.dto.MemberDetail;
import com.backend.allreva.member.query.application.dto.MemberDetail.MemberArtistDetail;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDetailRepositoryImpl implements MemberDetailRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberDetail findById(final Long id) {
        return queryFactory.select(memberDetailProjections())
                .from(member)
                .leftJoin(memberArtist).on(memberArtist.memberId.eq(member.id))
                .leftJoin(artist).on(artist.id.eq(memberArtist.artistId))
                .where(member.id.eq(id))
                .fetchFirst();
    }

    private ConstructorExpression<MemberDetail> memberDetailProjections() {
        return Projections.constructor(MemberDetail.class,
                member.email.email,
                member.memberInfo.nickname,
                member.memberInfo.introduce,
                member.memberInfo.profileImageUrl,
                Projections.list(Projections.constructor(MemberArtistDetail.class,
                        memberArtist.artistId,
                        artist.name
                )),
                member.refundAccount
        );
    }
}
