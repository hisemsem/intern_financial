//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
@RequestMapping({"/api/external/exchange-rates"})
public class ExternalExchangeRateController extends CoreAbstractServiceImpl {
    private final WebClient webClient;
    private final String authKey = "sM1MNyRX0rMgzbifcEpcjGTMHJuRrdtf";
    private final String EXTERNAL_API_BASE_URL = "https://www.koreaexim.go.kr";

    @Autowired
    public ExternalExchangeRateController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.koreaexim.go.kr").build();
    }

    @GetMapping(
        produces = {"application/json"}
    )
    public Mono<String> fetchExternalData(@RequestParam("searchDate") String searchDate, @RequestParam("dataCode") String dataCode) {
        return ((WebClient.RequestHeadersSpec)((WebClient.RequestHeadersSpec)this.webClient.get().uri((uriBuilder) -> {
            return uriBuilder.path("/site/program/financial/exchangeJSON").queryParam("authkey", new Object[]{"sM1MNyRX0rMgzbifcEpcjGTMHJuRrdtf"}).queryParam("searchdate", new Object[]{searchDate}).queryParam("data", new Object[]{dataCode}).build(new Object[0]);
        })).accept(new MediaType[]{MediaType.APPLICATION_JSON})).retrieve().bodyToMono(String.class);
    }
}
