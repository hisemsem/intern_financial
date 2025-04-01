package com.skylark.lon.api.interest.service;

import com.skylark.frm.core.map.CoreMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InterestService {
    List<CoreMap> getInterest (CoreMap data);

    void updateInterest (String id, CoreMap data);

    /**
     * API 테스트용 : 테스트종료후 코드 삭제!!
     */
    List<CoreMap> LONPMGTST00222 (CoreMap param) throws Exception;

    List<CoreMap> LONPMGTST00221 (CoreMap param) throws Exception;

    public void LONPMGTST009 (CoreMap param) throws Exception;

    public void LONPMGTST019 (CoreMap param) throws Exception;

//    public CoreMap LONAPIEXT00221 (String authKey, String languageType, String startCount, String endCount, String statCode);
    
//    public Mono<String> LONAPIEXT00222 (String authKey, String languageType, String startCount, String endCount, String statCode);
    
//    public Mono<String> LONAPIEXT00223 (String authKey,String languageType, String startCount, String endCount, String statCode, String cycle, String startDate, String endDate,String itemCode, String itemCode2, String itemCode3, String itemCode4);
    
    public void storeStatData (List<CoreMap> data) throws Exception;

	/**
	 * 외부 Open API에서 데이터를 불러옵니다.
	 *
	 * @param 한은 API data 정보를 포함하는 CoreMap 리스트
	 */
	CoreMap LONAPIEXT00221(CoreMap data);
	
	CoreMap LONAPIEXT00222(CoreMap data);
	
	CoreMap LONAPIEXT00223(CoreMap data);
	
	public void deleteOriginalMaster();
	
	public void deleteOriginalItem();
	
	public void deleteOriginalData();
	
//	public void deleteMasterStatCode(CoreMap newMaster);
	
	//[306010] Data Api Search
	public List<CoreMap> LONAPITST00222 (CoreMap param) throws Exception;
		
	public CoreMap insertNewMaster(CoreMap result, List<CoreMap> originResult);
	
	public CoreMap insertNewItem(CoreMap result, List<CoreMap> originResult);

	public CoreMap insertNewData(CoreMap result, List<CoreMap> originResult, String newCycle);

}
