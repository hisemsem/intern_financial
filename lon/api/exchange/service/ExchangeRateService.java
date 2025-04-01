package com.skylark.lon.api.exchange.service;

import com.skylark.frm.core.map.CoreMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExchangeRateService {
    List<CoreMap> getCurrentExchangeRates (CoreMap data);

    void updateExchangeRate (String id, CoreMap data);

    Mono<String> fetchExternalData (String searchDate, String dataCode);

}
