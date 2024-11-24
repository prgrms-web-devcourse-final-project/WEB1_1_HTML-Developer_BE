package com.backend.allreva.hall.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.hall.query.application.ConcertHallQueryService;
import com.backend.allreva.hall.query.application.dto.ConcertHallDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/concert-halls")
@RestController
public class ConcertHallController {

    private final ConcertHallQueryService concertHallQueryService;

    @GetMapping("/{hallCode}")
    public Response<ConcertHallDetail> findHallDetailByHallCode(
            @PathVariable("hallCode") String hallCode
    ) {
        ConcertHallDetail details = concertHallQueryService.findDetailByHallCode(hallCode);
        return Response.onSuccess(details);
    }
}
