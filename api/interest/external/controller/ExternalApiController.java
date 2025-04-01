//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.interest.external.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({"/api/external/datas"})
public class ExternalApiController extends CoreAbstractServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalApiController.class);
    private final WebClient webClient;
    private final String authKey = "N713VG3UA6O7ZYHSOXUP";
    private final String EXTERNAL_API_BASE_URL = "https://ecos.bok.or.kr/api";
    private final String requestType = "json";

    @Autowired
    public ExternalApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://ecos.bok.or.kr/api").build();
    }

    @GetMapping(
        value = {"/StatisticTableList"},
        produces = {"application/json"}
    )
    public Mono<String> fetchStatTableList(@RequestParam("languageType") String languageType, @RequestParam("startCount") String startCount, @RequestParam("endCount") String endCount) {
        LOGGER.debug("Controller Paramters: languageType={}, startCount={}, endCount={}", new Object[]{languageType, startCount, endCount});
        return ((WebClient.RequestHeadersSpec)((WebClient.RequestHeadersSpec)this.webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/StatisticTableList/N713VG3UA6O7ZYHSOXUP/json/" + languageType + "/" + startCount + "/" + endCount);
            return uriBuilder.build(new Object[0]);
        })).accept(new MediaType[]{MediaType.APPLICATION_JSON})).retrieve().bodyToMono(String.class).doOnSuccess((response) -> {
            LOGGER.debug("Response from external API: {}", response);
        }).doOnError((error) -> {
            LOGGER.error("Error calling external API", error);
        });
    }

    @GetMapping(
        value = {"/StatisticItemList"},
        produces = {"application/json"}
    )
    public Mono<String> fetchItemTableList(@RequestParam("languageType") String languageType, @RequestParam("startCount") String startCount, @RequestParam("endCount") String endCount, @RequestParam("statCode") String statCode) {
        return ((WebClient.RequestHeadersSpec)((WebClient.RequestHeadersSpec)this.webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/StatisticItemList/N713VG3UA6O7ZYHSOXUP/json/" + languageType + "/" + startCount + "/" + endCount + "/" + statCode);
            return uriBuilder.build(new Object[0]);
        })).accept(new MediaType[]{MediaType.APPLICATION_JSON})).retrieve().bodyToMono(String.class);
    }

    @GetMapping(
        value = {"/StatisticSearch"},
        produces = {"application/json"}
    )
    public Mono<String> fetchDataTableList(@RequestParam("languageType") String languageType, @RequestParam("startCount") String startCount, @RequestParam("endCount") String endCount, @RequestParam("statCode") String statCode, @RequestParam("cycle") String cycle, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam(value = "itemCode1",required = false) String itemCode1) {
        return ((WebClient.RequestHeadersSpec)((WebClient.RequestHeadersSpec)this.webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/StatisticSearch/N713VG3UA6O7ZYHSOXUP/json/" + languageType + "/" + startCount + "/" + endCount + "/" + statCode + "/" + cycle + "/" + startDate + "/" + endDate);
            if (itemCode1 != null) {
                uriBuilder.path("/" + itemCode1);
            }

            return uriBuilder.build(new Object[0]);
        })).accept(new MediaType[]{MediaType.APPLICATION_JSON})).retrieve().bodyToMono(String.class);
    }
}
