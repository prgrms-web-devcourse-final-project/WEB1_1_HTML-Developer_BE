package com.backend.allreva.concert.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.concert.command.application.AdminConcertService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/admin/concerts")
@RequiredArgsConstructor
public class AdminConcertController {     //TODO : 추후 관리자 권한 설정
    private final AdminConcertService adminConcertService;

    @Operation(
            summary = "콘서트 fetch API",
            description = "request param을 콘서트 정보를 받아오고 싶은 년도와 달을 주세요 ex)2024, 9."
    )
    @PostMapping
    public Response<Void> fetchConcertInfoList(@RequestParam("year") int year,
                                               @RequestParam("month") int month) {
        adminConcertService.fetchConcertInfoList(year, month);
        return Response.onSuccess();
    }
}
