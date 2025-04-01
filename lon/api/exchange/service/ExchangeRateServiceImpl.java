package com.skylark.lon.api.exchange.service;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.util.CoreJsonUtil;
import com.skylark.lon.api.exchange.external.controller.ExternalExchangeRateController;
import com.skylark.lon.api.exchange.mapper.ExchangeRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 환율 데이터 관리를 위한 서비스 클래스입니다.
 */
@Service("bsApiExchangeRateService")
public class ExchangeRateServiceImpl extends CoreAbstractServiceImpl implements ExchangeRateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
    private final ExchangeRateMapper exchangeRateMapper;

    private final ExternalExchangeRateController externalExchangeRateController;

    /**
     * 필요한 의존성을 주입받아 새로운 서비스 인스턴스를 생성합니다.
     *
     * @param exchangeRateMapper             환율 데이터에 접근하기 위한 데이터 액세스 객체
     * @param externalExchangeRateController 외부 API에서 데이터를 가져오는 컨트롤러
     */
    @Autowired
    public ExchangeRateServiceImpl (@Qualifier("bmApiExchangeRateMapper") ExchangeRateMapper exchangeRateMapper, ExternalExchangeRateController externalExchangeRateController) {
        this.exchangeRateMapper = exchangeRateMapper;
        this.externalExchangeRateController = externalExchangeRateController;
    }

    /**
     * 제공된 데이터 매개변수를 기반으로 현재 환율을 검색합니다.
     *
     * @param data 쿼리 매개변수를 포함하는 CoreMap
     * @return CoreMap 형식으로 표현된 환율 목록
     */
    @Override
    public List<CoreMap> getCurrentExchangeRates (CoreMap data) {
        //String dbRequire =
        //String[] arryDbRequire = dbRequire.split(",");
        //param.put("arryDbRequire", arryDbRequire);

        //return ExchangeRateMapper.selectTablesListFrmAppCmm(param);

        List<CoreMap> result = new ArrayList<>();

        return result;
    }

    /**
     * 데이터베이스에 있는 기존의 환율 정보를 업데이트합니다.
     *
     * @param id   업데이트할 환율의 고유 식별자
     * @param data 업데이트될 데이터를 포함하는 CoreMap
     */
    @Override
    public void updateExchangeRate (String id, CoreMap data) {
        // 구현이 필요합니다.
    }

    /**
     * 외부 API로부터 데이터를 가져옵니다.
     *
     * @param searchDate 환율을 검색할 날짜
     * @param dataCode   데이터를 가져올 때 사용할 데이터 코드
     * @return Mono<String> 비동기적으로 데이터를 반환하는 Mono
     */
    public Mono<String> fetchExternalData (String searchDate, String dataCode) {
        return externalExchangeRateController.fetchExternalData(searchDate, dataCode);
    }

    /**
     * JSON 문자열을 CoreMap 리스트로 변환합니다.
     *
     * @param rawData JSON 형태의 원시 문자열 데이터
     * @return List<CoreMap> 변환된 데이터 리스트
     */
    public List<CoreMap> transformData (String rawData) {
        try {
            return CoreJsonUtil.getListCoreMapFromJson(rawData);
        } catch (Exception e) {
            LOGGER.error("JSON을 CoreMap으로 변환하는 과정에서 오류 발생", e);
            throw new RuntimeException("JSON을 CoreMap으로 변환하는 과정에서 오류 발생", e);
        }
    }

    /**
     * 데이터베이스에 환율 데이터를 저장합니다.
     *
     * @param data 환율 정보를 포함하는 CoreMap 리스트
     */
    public void storeData (List<CoreMap> data) {
        for (CoreMap dataEntry : data) {
            exchangeRateMapper.insertRatesFromExternalApi(dataEntry);
        }
    }

    /**
     * 데이터를 가져오고, 변환한 후 저장하는 과정을 관리합니다.
     */
    public void fetchProcessAndStoreData (String searchDate, String dataCode) {
        fetchExternalData(searchDate, dataCode).subscribe(rawData -> {
            try {
                List<CoreMap> dataList = transformData(rawData);
                storeData(dataList);
            } catch (Exception e) {
                LOGGER.error("데이터 처리 중 오류 발생", e);
            }
        }, error -> LOGGER.error("API 호출 중 오류 발생", error));
    }

}
