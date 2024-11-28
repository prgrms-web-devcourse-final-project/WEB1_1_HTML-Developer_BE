package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.infra.dto.KopisConcertResponse;
import com.backend.allreva.concert.infra.dto.KopisConcertCodeResponse;
import com.backend.allreva.hall.command.application.dto.KopisHallResponse;
import feign.codec.Decoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "OpenFeignClient", url = "https://www.kopis.or.kr/openApi/restful", configuration = OpenFeignClient.Configuration.class)
public interface OpenFeignClient {
    @GetMapping("${public-data.kopis.prfplc-url}")
    KopisConcertCodeResponse fetchConcertCodes(@RequestParam(value = "prfplccd") String hallCode,
                                               @RequestParam(value = "stdate")String startDate,
                                               @RequestParam(value = "eddate")String endDate,
                                               @RequestParam(value = "afterDate", required = false) String today);

    @GetMapping("${public-data.kopis.prf-url}")
    KopisConcertResponse fetchConcertDetail(@PathVariable(value = "concertCode") String concertCode);

    @GetMapping("${public-data.kopis.prfplc-detail-url}")
    KopisHallResponse fetchConcertHallInfoList(@PathVariable(value = "hallCode") String hallCode);

    class Configuration {
        @Bean
        public Decoder xmlDecoder() {
            return new JAXBDecoder(new JAXBContextFactory.Builder()
                    .withMarshallerJAXBEncoding("UTF-8")
                    .build());
        }
    }
}
