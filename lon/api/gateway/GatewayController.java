package com.skylark.lon.api.gateway;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.util.CoreJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 비동기 HTTP 요청을 처리하는 EndPoint 게이트웨이 컨트롤러입니다.
 *
 * @author KimJunSeob
 */
@RestController
@RequestMapping("/api")
public class GatewayController extends CoreAbstractServiceImpl {

    private final WebClient webClient;

    private final String GATEWAY_BASE_URL = "http://localhost:8080/skylark-lon/";
    private final String EXCHANGE_RATE_SERVICE_URL = "http://localhost:8080/skylark-lon/exchange-rates";
    private final String INTEREST_SERVICE_URL = "http://localhost:8080/skylark-lon/interests";

    @Autowired
    public GatewayController (WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GATEWAY_BASE_URL).build(); // 기본 베이스 URL 설정
    }

    @PostMapping("/exchange-rates")
    public Mono<Object> getExchangeRate (@RequestBody CoreMap param) throws Exception {
        String exchangeRateJson = String.valueOf(CoreJsonUtil.getCoreMapToJson(param));
        return webClient.post()
            .uri(EXCHANGE_RATE_SERVICE_URL) // 내부 서비스의 엔드포인트로 요청을 전달
            .contentType(MediaType.APPLICATION_JSON)// 요청의 콘텐츠 타입을 JSON으로 설정
            .bodyValue(exchangeRateJson)// 요청 본문에 JSON 데이터를 설정
            .retrieve()// 요청을 전송하고 응답을 받음
            .bodyToMono(String.class)// 응답 본문을 String 타입의 Mono로 변환
            .map(response -> {
                try {
                    return CoreJsonUtil.getCoreMapFromJson(response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });// 응답 본문을 CoreMap으로 변환
    }

    @PostMapping("/interests")
    public Mono<Object> getInterest (@RequestBody CoreMap param) {
        String interestJson = String.valueOf(CoreJsonUtil.getCoreMapToJson(param));
        return webClient.post()
            .uri(INTEREST_SERVICE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(interestJson)
            .retrieve()
            .bodyToMono(String.class)
            .map(response -> {
                try {
                    return CoreJsonUtil.getCoreMapFromJson(response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }

}
