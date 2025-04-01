//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.exchange.service;

import com.skylark.frm.core.map.CoreMap;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {
    List<CoreMap> getCurrentExchangeRates(CoreMap var1);

    void updateExchangeRate(String var1, CoreMap var2);

    Mono<String> fetchExternalData(String var1, String var2);
}
