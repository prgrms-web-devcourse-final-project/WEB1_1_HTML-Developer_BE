package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.command.application.AdminConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/admin/concerts")
@RequiredArgsConstructor
public class AdminConcertController {     //TODO : 추후 관리자 권한 설정
    private final AdminConcertService adminConcertService;

    @PostMapping
    public Response<Void> fetchConcertInfoList() {
        adminConcertService.fetchConcertInfoList(LocalDate.of(2024,10,01),
                LocalDate.of(2025,01,31));
        return Response.onSuccess();
    }
}
