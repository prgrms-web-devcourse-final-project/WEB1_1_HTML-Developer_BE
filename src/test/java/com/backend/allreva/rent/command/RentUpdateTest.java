package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentFixture.createRentFixture;
import static com.backend.allreva.rent.fixture.RentUpdateRequestFixture.createRentUpdateRequestFixture;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.member.command.domain.Member;
import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.domain.Rent;
import com.backend.allreva.rent.command.domain.RentRepository;
import com.backend.allreva.rent.exception.RentAccessDeniedException;
import com.backend.allreva.rent.exception.RentNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentUpdateTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentRepository rentRepository;

    @Test
    void 차량_대절_폼_수정을_성공한다() {
        // given
        var user = createMockUser(1L);
        var rentRequest = createRentUpdateRequestFixture(1L);
        given(rentRepository.findById(anyLong())).willReturn(Optional.of(createRentFixture(1L, 1L)));
        given(rentRepository.save(any(Rent.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        rentCommandService.updateRent(rentRequest, user);

        // then
        var capturedRentForm = getArgumentCaptorValue();
        assertSoftly(softly -> {
            softly.assertThat(capturedRentForm.getMemberId()).isEqualTo(user.getId());
            softly.assertThat(capturedRentForm.getAdditionalInfo().getInformation()).isEqualTo(rentRequest.information());
        });
    }

    @Test
    void 차량_대절_폼이_작성자_본인이_아니라면_예외를_발생시킨다() {
        // given
        var user = createMockUser(2L);
        var rentUpdateRequest = createRentUpdateRequestFixture(1L);
        given(rentRepository.findById(anyLong())).willReturn(Optional.of(createRentFixture(1L, 1L)));

        // when & then
        assertThrows(RentAccessDeniedException.class,
                () -> rentCommandService.updateRent(rentUpdateRequest, user));
        verify(rentRepository, times(1)).findById(anyLong());
    }

    @Test
    @Transactional
    void 차량_대절_폼이_없을_경우_예외를_발생시킨다() {
        // given
        var user = createMockUser(2L);
        var rentUpdateRequest = createRentUpdateRequestFixture(1L);
        given(rentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(RentNotFoundException.class,
                () -> rentCommandService.updateRent(rentUpdateRequest, user));
    }

    private Member createMockUser(Long userId) {
        var user = Mockito.mock(Member.class);
        lenient().when(user.getId()).thenReturn(userId);
        return user;
    }

    private Rent getArgumentCaptorValue() {
        var rentFormCaptor = ArgumentCaptor.forClass(Rent.class);
        verify(rentRepository).save(rentFormCaptor.capture());
        return rentFormCaptor.getValue();
    }
}
