package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentJoinApplyRequestFixture.createRentJoinApplyRequestFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent_join.command.domain.RentJoin;
import com.backend.allreva.rent_join.command.domain.RentJoinRepository;
import com.backend.allreva.rent.command.domain.RentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentJoinApplyTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentRepository rentRepository;
    @Mock
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_신청_폼을_지원한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoinApplyRequest = createRentJoinApplyRequestFixture(rentId);
        given(rentRepository.existsById(rentJoinApplyRequest.rentId())).willReturn(true);
        given(rentJoinRepository.save(any(RentJoin.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.applyRent(rentJoinApplyRequest, memberId);

        // then
        var capturedRentJoin = getArgumentCaptorValue();
        assertSoftly(softly -> {
            softly.assertThat(capturedRentJoin.getMemberId()).isEqualTo(memberId);
            softly.assertThat(capturedRentJoin.getRentId()).isEqualTo(rentId);
        });
    }

    private RentJoin getArgumentCaptorValue() {
        var rentFormCaptor = ArgumentCaptor.forClass(RentJoin.class);
        verify(rentJoinRepository).save(rentFormCaptor.capture());
        return rentFormCaptor.getValue();
    }
}
