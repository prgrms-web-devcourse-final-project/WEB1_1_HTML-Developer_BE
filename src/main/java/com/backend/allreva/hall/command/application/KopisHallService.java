package com.backend.allreva.hall.command.application;

import com.backend.allreva.hall.infra.dto.KopisHallResponse;

public interface KopisHallService {
    KopisHallResponse fetchConcertHallInfoList(String HallCode);
}
