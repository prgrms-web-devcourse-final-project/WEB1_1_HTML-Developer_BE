package com.backend.allreva.hall.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.hall.command.application.AdminHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/halls")
@RequiredArgsConstructor
public class AdminHallController {     //TODO : 추후 관리자 권한 설정
    private final AdminHallService adminHallService;

    @PostMapping
    public Response<Void> fetchConcertHallInfoList() {
        adminHallService.fetchConcertHallInfoList();
        return Response.onSuccess();
    }
}
