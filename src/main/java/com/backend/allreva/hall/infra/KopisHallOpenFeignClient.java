package com.backend.allreva.hall.infra;

import com.backend.allreva.common.feign.OpenFeignClient;
import com.backend.allreva.hall.command.application.KopisHallService;
import com.backend.allreva.hall.infra.dto.KopisHallResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KopisHallOpenFeignClient implements KopisHallService {
    private final OpenFeignClient openFeignClient;

    @Override
    public KopisHallResponse fetchConcertHallInfoList(String hallCode) {
        return openFeignClient.fetchConcertHallDetail(hallCode);
    }

}
