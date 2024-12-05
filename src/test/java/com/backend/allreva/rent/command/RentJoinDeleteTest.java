package com.backend.allreva.rent.command;

import static com.backend.allreva.rent.fixture.RentJoinFixture.createRentJoinFixture;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.backend.allreva.rent.command.application.RentCommandService;
import com.backend.allreva.rent.command.application.dto.RentJoinIdRequest;
import com.backend.allreva.rent.command.domain.RentJoinRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class RentJoinDeleteTest {

    @InjectMocks
    private RentCommandService rentCommandService;
    @Mock
    private RentJoinRepository rentJoinRepository;

    @Test
    void 차량_대절_신청_폼을_삭제한다() {
        // given
        var memberId = 1L;
        var rentId = 1L;
        var rentJoinId = 1L;
        var rentJoin = createRentJoinFixture(rentId, memberId);
        var rentJoinIdRequest = new RentJoinIdRequest(rentJoinId);
        given(rentJoinRepository.findById(rentJoinId)).willReturn(Optional.of(rentJoin));

        // when
        rentCommandService.deleteRentJoin(rentJoinIdRequest, memberId);

        // then
        verify(rentJoinRepository, times(1)).delete(rentJoin);
    }
}
