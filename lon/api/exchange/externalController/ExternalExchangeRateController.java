package com.skylark.lon.api.exchange.external.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external/exchange-rates")
public class ExternalExchangeRateController extends CoreAbstractServiceImpl {

    private final WebClient webClient;
    private final String authKey = "sM1MNyRX0rMgzbifcEpcjGTMHJuRrdtf"; // 한국 수출입 은행의 API 인증 key
    private final String EXTERNAL_API_BASE_URL = "https://www.koreaexim.go.kr";

    @Autowired
    public ExternalExchangeRateController (WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(EXTERNAL_API_BASE_URL).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> fetchExternalData (
        @RequestParam("searchDate") String searchDate,
        @RequestParam("dataCode") String dataCode) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/site/program/financial/exchangeJSON")
                .queryParam("authkey", authKey)
                .queryParam("searchdate", searchDate)
                .queryParam("data", dataCode)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    } // 외부 API 에서 환율 데이터를 직접 호출하여 JSON 형식의 응답을 문자열로 반환
}
