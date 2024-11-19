package com.kwanse.allreva.concert.ui;

import com.kwanse.allreva.common.dto.Response;
import com.kwanse.allreva.concert.command.application.AdminConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/concerts")
@RequiredArgsConstructor
public class AdminConcertController {
    private final AdminConcertService adminConcertService;

    //TODO : 추후 관리자 권한 붙이기
    @PostMapping("/info")
    public Response<Void> fetchConcertInfoList() {
        adminConcertService.fetchConcertInfoList();
        return Response.onSuccess();
    }
}
