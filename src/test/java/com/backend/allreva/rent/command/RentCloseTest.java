package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.dto.RentIdRequest;
import com.backend.allreva.rent.command.domain.RentRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentCloseTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentRepository rentRepository;

    @Test
    void 차량_대절_폼_마감을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentFormIdRequest = new RentIdRequest(1L);
        var rent = createRentFixture(user.getId(), 1L);
        given(rentRepository.findById(anyLong())).willReturn(Optional.of(rent));

        // when
        rentCommandService.closeRent(rentFormIdRequest, user);

        // then
        assertSoftly(softly -> {
            softly.assertThat(rent.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(rent.isClosed()).isTrue();
        });
    }

    private Member createMockUser(final Long userId) {
        var user = Mockito.mock(Member.class);
        when(user.getId()).thenReturn(userId);
        return user;
    }
}
