package com.skylark.lon.api.interest.external.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.lon.api.interest.service.InterestServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external/datas")
public class ExternalApiController extends CoreAbstractServiceImpl {

	  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalApiController.class);
	  
    private final WebClient webClient; 
    private final String authKey = "N713VG3UA6O7ZYHSOXUP"; // 한국 수출입 은행의 API 인증 key
    private final String EXTERNAL_API_BASE_URL = "https://ecos.bok.or.kr/api";
    private final String requestType = "json";
    @Autowired
    public ExternalApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(EXTERNAL_API_BASE_URL).build();
    }

 
    
    @GetMapping(value = "/StatisticTableList", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> fetchStatTableList(
        @RequestParam("languageType") String languageType,
        @RequestParam("startCount") String startCount,
        @RequestParam("endCount") String endCount
//        ,@RequestParam(value = "statCode", required = false) String statCode // Optional parameter
        
    ) {
    	LOGGER.debug("Controller Paramters: languageType={}, startCount={}, endCount={}, statCode={}", 
                            languageType, startCount, endCount);
        return webClient.get()
            .uri(uriBuilder -> {
            	 uriBuilder.path("/StatisticTableList/" + authKey + "/" + requestType + "/" + languageType + "/" + startCount + "/" + endCount);
//                 if (statCode != null) {
//                     uriBuilder.path("/" + statCode);
//                 }
                return uriBuilder.build();
            })
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class) //비동기적으로 String을 보냄
            .doOnSuccess(response -> LOGGER.debug("Response from external API: {}", response))
            .doOnError(error -> LOGGER.error("Error calling external API", error)); // 비동기적으로 String을 보냄
    }

    @GetMapping(value = "/StatisticItemList", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> fetchItemTableList(
        @RequestParam("languageType") String languageType,
        @RequestParam("startCount") String startCount,
        @RequestParam("endCount") String endCount,
        @RequestParam("statCode") String statCode
         
    ) {
        return webClient.get()
            .uri(uriBuilder -> {
                uriBuilder.path("/StatisticItemList/" + authKey + "/" + requestType + "/" + languageType + "/" + startCount + "/" + endCount + "/" + statCode);
                LOGGER.debug("hi");
                return uriBuilder.build();
            })
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    }

    @GetMapping(value = "/StatisticSearch",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<String> fetchDataTableList(
        @RequestParam("languageType") String languageType,
        @RequestParam("startCount") String startCount,
        @RequestParam("endCount") String endCount,
        @RequestParam("statCode") String statCode,
        @RequestParam("cycle") String cycle,
        @RequestParam("startDate") String startDate,
        @RequestParam("endDate") String endDate,
        @RequestParam(value = "itemCode1", required = false) String itemCode1, // Optional parameter
        @RequestParam(value = "itemCode2", required = false) String itemCode2, // Optional parameter
        @RequestParam(value = "itemCode3", required = false) String itemCode3, // Optional parameter
        @RequestParam(value = "itemCode4", required = false) String itemCode4 // Optional parameter
    ) {
    	   return webClient.get()
            .uri(uriBuilder -> {
                uriBuilder.path("/StatisticSearch/" + authKey + "/" + requestType + "/" + languageType + "/" + startCount + "/" + endCount + "/" + statCode + "/" + cycle + "/" + startDate + "/" + endDate);
                
                
                if (itemCode1 != null) {
                	  uriBuilder.path("/" + itemCode1);
                }
                if (itemCode2 != null) {
                	 uriBuilder.path("/" + itemCode2);
                }
                if (itemCode3 != null) {
                	 uriBuilder.path("/" + itemCode3);
                }
                if (itemCode4 != null) {
                	 uriBuilder.path("/" + itemCode4);
                }
                return uriBuilder.build();
            })
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    } // 외부 API 에서 데이터를 직접 호출하여 JSON 형식의 응답을 문자열로 반환


}
