package com.skylark.lon.api.exchange.mapper;

import com.skylark.frm.core.annotation.CoreMapper;
import com.skylark.frm.core.map.CoreMap;

import java.util.List;

@CoreMapper("bmApiExchangeRateMapper")
public interface ExchangeRateMapper {
    void insertRatesFromExternalApi (CoreMap param);

    List<CoreMap> getRates (CoreMap param);

}
