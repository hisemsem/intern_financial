package com.skylark.lon.api.exchange.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.lon.api.exchange.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController extends CoreAbstractServiceImpl {

  private ExchangeRateService exchangeRateService;
  public ExchangeRateController(ExchangeRateService exchangeRateService) {
    this.exchangeRateService = exchangeRateService;
  }

  // Query Endpoint - 데이터 조회
  @GetMapping
  public ResponseEntity<CoreMap> getExchangeRates(@RequestParam CoreMap data) {
    //fixme : testMap 추후 삭제요망
    CoreMap testMap = new CoreMap();
    testMap.put("testData", data);
    List<CoreMap> exchangeRates = (List<CoreMap>) exchangeRateService.getCurrentExchangeRates(testMap); // 고쳐야함
    return ResponseEntity.ok((CoreMap) exchangeRates);
  }

  // Command Endpoint - 데이터 갱신
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateExchangeRate(@PathVariable String id, @RequestBody CoreMap data) {
    exchangeRateService.updateExchangeRate(id, data);
    return ResponseEntity.ok().build();
  }

  // 환율 정보 조회
  @GetMapping("/external.do")
  public Mono<ResponseEntity<CoreMap>> getExternalExchangeRates(
      @RequestParam String searchDate,
      @RequestParam String dataCode) {
    return exchangeRateService.fetchExternalData(searchDate, dataCode)
        .map(data -> {
          CoreMap responseMap = new CoreMap();
          responseMap.put("exchangeRates", data);
          return ResponseEntity.ok(responseMap);
        })
        .defaultIfEmpty(ResponseEntity.notFound().build());
  } //외부 API에서 환율 정보를 가져와서 CoreMap으로 변환 후 클라이언트에게 반환

}
