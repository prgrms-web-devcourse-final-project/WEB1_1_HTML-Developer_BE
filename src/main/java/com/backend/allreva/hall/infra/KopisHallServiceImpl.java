package com.backend.allreva.hall.infra;

import com.backend.allreva.hall.command.application.KopisHallService;
import com.backend.allreva.hall.command.application.dto.KopisHallResponse;
import com.backend.allreva.hall.infra.dto.Pfmplc;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KopisHallServiceImpl implements KopisHallService {
    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    @Value("${public-data.kopis.prfplc-detail-url}")
    private String ConcertHallUrl;

    public KopisHallServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public Mono<KopisHallResponse> fetchConcertHallDetail(String hallId) {
        return webClient.get()
                .uri(UriComponentsBuilder.fromUriString(ConcertHallUrl)
                        .buildAndExpand(getFacilityId(hallId))
                        .toUriString())
                .retrieve()
                .bodyToMono(String.class)// 비동기적으로 String을 반환
                .map(xml -> {  // xml을 받아서 처리하는 부분
                    try {
                        JsonNode rootNode = xmlMapper.readTree(xml);
                        JsonNode node = rootNode.path("db");
                        return toKopisConcertHallResponse(hallId, node);
                    } catch (Exception e) {
                        log.error("Can't fetch hallId detail: hallId {}", hallId);
                        log.error("Error Message: {}", e.getMessage());
                        return null;
                    }
                });
    }


    private String getFacilityId(String hallId) {
        return hallId.split("-")[0];
    }

    private KopisHallResponse toKopisConcertHallResponse(String hallId, JsonNode node) {

        String fcltynm = node.get("fcltynm").asText();
        String adres = node.get("adres").asText();
        String la = node.get("la").asText();
        String lo = node.get("lo").asText();
        String restaurant = node.get("restaurant").asText();
        String cafe = node.get("cafe").asText();
        String store = node.get("store").asText();
        String parkinglot = node.get("parkinglot").asText();
        String parkbarrier = node.get("parkbarrier").asText();
        String restbarrier = node.get("restbarrier").asText();
        String runwbarrier = node.get("runwbarrier").asText();
        String elevbarrier = node.get("elevbarrier").asText();

        JsonNode mt13 = node.get("mt13s").get("mt13");
        Pfmplc prfplc = getPfmplc(mt13, hallId);

        return new KopisHallResponse(fcltynm, prfplc.getPrfplcnm(), prfplc.getSeatscale(), prfplc.getMt13id(),
                adres, la, lo, restaurant, cafe, store, parkinglot, parkbarrier, restbarrier, runwbarrier, elevbarrier);

    }

    private Pfmplc getPfmplc(JsonNode mt13, String hallId) {
        if (mt13.isArray()) {
            for (JsonNode one : mt13) {
                if (one.path("mt13id").asText().equals(hallId)) {
                    String prfplcnm = one.path("prfplcnm").asText();
                    String seatscale = one.path("seatscale").asText();
                    String mt13id = one.path("mt13id").asText();
                    return new Pfmplc(prfplcnm, seatscale, mt13id);
                }
            }
        } else {
            String prfplcnm = mt13.get("prfplcnm").asText();
            String seatscale = mt13.get("seatscale").asText();
            String mt13id = mt13.get("mt13id").asText();
            return new Pfmplc(prfplcnm, seatscale, mt13id);
        }

        return null;
    }
}
