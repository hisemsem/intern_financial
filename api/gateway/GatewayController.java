//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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

@RestController
@RequestMapping({"/api"})
public class GatewayController extends CoreAbstractServiceImpl {
    private final WebClient webClient;
    private final String GATEWAY_BASE_URL = "http://localhost:8080/skylark-lon/";
    private final String EXCHANGE_RATE_SERVICE_URL = "http://localhost:8080/skylark-lon/exchange-rates";
    private final String INTEREST_SERVICE_URL = "http://localhost:8080/skylark-lon/interests";

    @Autowired
    public GatewayController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/skylark-lon/").build();
    }

    @PostMapping({"/exchange-rates"})
    public Mono<Object> getExchangeRate(@RequestBody CoreMap param) throws Exception {
        String exchangeRateJson = String.valueOf(CoreJsonUtil.getCoreMapToJson(param));
        return ((WebClient.RequestBodySpec)this.webClient.post().uri("http://localhost:8080/skylark-lon/exchange-rates", new Object[0])).contentType(MediaType.APPLICATION_JSON).bodyValue(exchangeRateJson).retrieve().bodyToMono(String.class).map((response) -> {
            try {
                return CoreJsonUtil.getCoreMapFromJson(response);
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        });
    }

    @PostMapping({"/interests"})
    public Mono<Object> getInterest(@RequestBody CoreMap param) {
        String interestJson = String.valueOf(CoreJsonUtil.getCoreMapToJson(param));
        return ((WebClient.RequestBodySpec)this.webClient.post().uri("http://localhost:8080/skylark-lon/interests", new Object[0])).contentType(MediaType.APPLICATION_JSON).bodyValue(interestJson).retrieve().bodyToMono(String.class).map((response) -> {
            try {
                return CoreJsonUtil.getCoreMapFromJson(response);
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        });
    }
}
