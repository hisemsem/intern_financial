//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.exchange.service;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.util.CoreJsonUtil;
import com.skylark.lon.api.exchange.external.controller.ExternalExchangeRateController;
import com.skylark.lon.api.exchange.mapper.ExchangeRateMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("bsApiExchangeRateService")
public class ExchangeRateServiceImpl extends CoreAbstractServiceImpl implements ExchangeRateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
    private final ExchangeRateMapper exchangeRateMapper;
    private final ExternalExchangeRateController externalExchangeRateController;

    @Autowired
    public ExchangeRateServiceImpl(@Qualifier("bmApiExchangeRateMapper") ExchangeRateMapper exchangeRateMapper, ExternalExchangeRateController externalExchangeRateController) {
        this.exchangeRateMapper = exchangeRateMapper;
        this.externalExchangeRateController = externalExchangeRateController;
    }

    public List<CoreMap> getCurrentExchangeRates(CoreMap data) {
        List<CoreMap> result = new ArrayList();
        return result;
    }

    public void updateExchangeRate(String id, CoreMap data) {
    }

    public Mono<String> fetchExternalData(String searchDate, String dataCode) {
        return this.externalExchangeRateController.fetchExternalData(searchDate, dataCode);
    }

    public List<CoreMap> transformData(String rawData) {
        try {
            return CoreJsonUtil.getListCoreMapFromJson(rawData);
        } catch (Exception var3) {
            LOGGER.error("JSON을 CoreMap으로 변환하는 과정에서 오류 발생", var3);
            throw new RuntimeException("JSON을 CoreMap으로 변환하는 과정에서 오류 발생", var3);
        }
    }

    public void storeData(List<CoreMap> data) {
        Iterator var3 = data.iterator();

        while(var3.hasNext()) {
            CoreMap dataEntry = (CoreMap)var3.next();
            this.exchangeRateMapper.insertRatesFromExternalApi(dataEntry);
        }

    }

    public void fetchProcessAndStoreData(String searchDate, String dataCode) {
        this.fetchExternalData(searchDate, dataCode).subscribe((rawData) -> {
            try {
                List<CoreMap> dataList = this.transformData(rawData);
                this.storeData(dataList);
            } catch (Exception var3) {
                LOGGER.error("데이터 처리 중 오류 발생", var3);
            }

        }, (error) -> {
            LOGGER.error("API 호출 중 오류 발생", error);
        });
    }
}
