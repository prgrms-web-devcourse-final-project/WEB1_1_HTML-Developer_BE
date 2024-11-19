package com.kwanse.allreva.concert.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kwanse.allreva.concert.command.application.KopisService;
import com.kwanse.allreva.concert.command.dto.KopisConcertResponse;
import com.kwanse.allreva.concert.command.dto.Relate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KopisServiceImpl implements KopisService {

    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    @Value("${public-data.kopis.prfplc-url}")
    private String findConcertIdUrl;

    @Value("${public-data.kopis.prf-url}")
    private String ConcertUrl;

    @Value("${public-data.kopis.stdate}")
    private String stdate;

    @Value("${public-data.kopis.eddate}")
    private String eddate;


    public KopisServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public Mono<List<String>> fetchConcertCodes(String id, boolean isDaily) {
        return webClient.get()
                .uri(buildConcertUri(id, isDaily))
                .retrieve()
                .bodyToMono(String.class)
                .map(xml -> {
                    try {
                        JsonNode rootNode = xmlMapper.readTree(xml);
                        List<String> concertIds = new ArrayList<>();
                        JsonNode dbArray = rootNode.get("db");

                        // 공연이 없는 경우 빈 리스트 반환
                        if (dbArray == null) return new ArrayList<>();
                        if (dbArray.isArray()) {
                            for (JsonNode node : dbArray) {
                                concertIds.add(node.get("mt20id").asText());
                            }
                        }

                        log.info("fetch concert ids complete : hallId {}", id);
                        return concertIds;
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("can't fetch concert ids : hallId {}", id);
                        return new ArrayList<>();
                    }
                });
    }

    private String buildConcertUri(String id, boolean isDaily) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(findConcertIdUrl)
                .queryParam("prfplccd", id);

        if (isDaily) {
            LocalDate today = LocalDate.now();
            String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            uriBuilder.queryParam("afterdate", formattedDate);
        }

        return uriBuilder.buildAndExpand(stdate, eddate).toUriString();
    }

    @Override
    public Mono<KopisConcertResponse> fetchConcertDetail(String id) {
        return webClient.get()
                .uri(UriComponentsBuilder.fromUriString(ConcertUrl)
                        .buildAndExpand(id)
                        .toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .map(xml -> {
                    try {
                        // XML을 JsonNode로 파싱
                        JsonNode rootNode = xmlMapper.readTree(xml);
                        JsonNode node = rootNode.path("db");

                        log.info("fetch concert info complete : concertId {}", id);

                        return toKopisResponse(node, id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("can't fetch concert info : concertId {}", id);
                        return null;
                    }
                });
    }

    private KopisConcertResponse toKopisResponse(JsonNode node, String concertHallId) {
        String mt20id = node.get("mt20id").asText();
        String prfnm = node.get("prfnm").asText();
        String prfpdfrom = node.get("prfpdfrom").asText();
        String prfpdto = node.get("prfpdto").asText();
        String prfstate = node.get("prfstate").asText();
        String pcseguidance = node.get("pcseguidance").asText();
        String poster = node.get("poster").asText();
        List<String> styurl = getStyurls(node);
        List<Relate> relate = getRelates(node);

        return new KopisConcertResponse(mt20id, prfnm, prfpdfrom, prfpdto, concertHallId, poster, pcseguidance, prfstate, styurl, relate);
    }

    private List<Relate> getRelates(JsonNode node) {
        List<Relate> relates = new ArrayList<>();
        JsonNode relatesNode = node.get("relates").get("relate");

        if (relatesNode.isArray()) {
            for (JsonNode one : relatesNode) {
                String relatenm = one.path("relatenm").asText();
                String relateurl = one.path("relateurl").asText();
                relates.add(new Relate(relatenm, relateurl));

            }
        } else {
            String relatenm = relatesNode.get("relatenm").asText();
            String relateurl = relatesNode.get("relateurl").asText();
            relates.add(new Relate(relatenm, relateurl));
        }

        return relates;
    }

    private List<String> getStyurls(JsonNode node) {
        JsonNode styurlsNode = node.get("styurls");
        if (styurlsNode == null) return new ArrayList<>();
        List<String> styurlList = new ArrayList<>();

        if (styurlsNode.get("styurl").isArray()) {
            for (JsonNode one : styurlsNode.get("styurl")) {
                styurlList.add(one.asText());
            }
        } else {
            styurlList.add(styurlsNode.get("styurl").asText());
        }

        return styurlList;
    }
}
