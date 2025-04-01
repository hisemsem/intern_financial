//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.exchange.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.lon.api.exchange.service.ExchangeRateService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({"/exchange-rates"})
public class ExchangeRateController extends CoreAbstractServiceImpl {
    private ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<CoreMap> getExchangeRates(@RequestParam CoreMap data) {
        CoreMap testMap = new CoreMap();
        testMap.put("testData", data);
        List<CoreMap> exchangeRates = this.exchangeRateService.getCurrentExchangeRates(testMap);
        return ResponseEntity.ok((CoreMap)exchangeRates);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Void> updateExchangeRate(@PathVariable String id, @RequestBody CoreMap data) {
        this.exchangeRateService.updateExchangeRate(id, data);
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/external.do"})
    public Mono<ResponseEntity<CoreMap>> getExternalExchangeRates(@RequestParam String searchDate, @RequestParam String dataCode) {
        return this.exchangeRateService.fetchExternalData(searchDate, dataCode).map((data) -> {
            CoreMap responseMap = new CoreMap();
            responseMap.put("exchangeRates", data);
            return ResponseEntity.ok(responseMap);
        }).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
