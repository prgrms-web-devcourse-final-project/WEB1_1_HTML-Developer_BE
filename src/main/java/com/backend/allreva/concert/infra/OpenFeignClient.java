package com.backend.allreva.concert.infra;

import com.backend.allreva.common.config.FeignConfig;
import com.backend.allreva.concert.command.application.dto.KopisConcertResponse;
import com.backend.allreva.concert.infra.dto.ConcertCodeResponse;
import com.backend.allreva.hall.command.application.dto.KopisHallResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "OpenFeignClient", url = "https://www.kopis.or.kr/openApi/restful", configuration = FeignConfig.class)
public interface OpenFeignClient {
    @GetMapping("${public-data.kopis.prfplc-url}")
    ConcertCodeResponse fetchConcertCodes(@RequestParam(value = "prfplccd") String hallCode,
                                          @RequestParam(value = "stdate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                          @RequestParam(value = "eddate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
                                          @RequestParam(value = "afterDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate today);

    @GetMapping("${public-data.kopis.prf-url}")
    KopisConcertResponse fetchConcertDetail(@PathVariable(value = "concertCode") String concertCode);

    @GetMapping("${public-data.kopis.prfplc-detail-url}")
    KopisHallResponse fetchConcertHallInfoList(@PathVariable(value = "hallCode") String hallCode);

}
