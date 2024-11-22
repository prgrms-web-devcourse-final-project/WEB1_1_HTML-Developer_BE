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
@RequestMapping("/concert-hall")
@RestController
public class ConcertHallController {

    private final ConcertHallQueryService concertHallQueryService;

    @GetMapping("/{facilityCode}")
    public Response<List<ConcertHallDetail>> findHallDetailByFacilityId(
            @PathVariable("facilityCode") String facilityCode
    ) {
        List<ConcertHallDetail> details = concertHallQueryService.findDetailByFacilityCode(facilityCode);
        return Response.onSuccess(details);
    }
}
