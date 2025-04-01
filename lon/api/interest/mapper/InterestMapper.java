package com.skylark.lon.api.interest.mapper;

import com.skylark.frm.core.annotation.CoreMapper;
import com.skylark.frm.core.map.CoreMap;

import java.util.List;

@CoreMapper("bmApiInterestMapper")
public interface InterestMapper {


    List<CoreMap> getInterest (CoreMap param);
    
    //외부 master data api 저장
    void insertExternalMasterDataApi (CoreMap param);
    
    //외부 item data api 저장
    void insertExternalItemDataApi (CoreMap param);
    
    //외부 stat data api 저장
    void insertExternalStatDataApi (CoreMap param);
 
    //102070 Left 화면 조회 테스트
    public List<CoreMap> searchMasterFrmAppEac (CoreMap param) throws Exception;

    //102070 right 화면 조회 테스트
    public List<CoreMap> searchItemFrmAppEac (CoreMap param) throws Exception;
 
    //102070 Left 화면 수정 테스트
    public void updateMasterFrmAppEac (CoreMap param) throws Exception;

    //102070 Right 화면 수정 테스트
     
    public void updateItemFrmAppEac (CoreMap param) throws Exception;

    public List<CoreMap> searchOriginalMaster();
    
    public List<CoreMap> searchOriginalItem();
    
    public List<CoreMap> searchOriginalData();
    
    public List<CoreMap> searchMatchedOriginalData(String newItemId);
    
    public void deleteOriginalMaster();
    
    public void deleteOriginalItem();
    
    public void deleteOriginalData();

	public void deleteItemStatCodeData(CoreMap deleteStatCode);
	
	public void deleteSearchStatCodeData(CoreMap deleteStatCode);
	
	public void deleteDataSearchItemCode(String deleteItemId);
	
    public void insertNewMasterData(CoreMap param);
    
    public void insertNewItemListData(CoreMap param);
    
    public void insertNewItemData(CoreMap param);
    
    public void insertNewRealData(CoreMap param);
    
    public List<CoreMap> searchMasterUsedFlag();
    
    public List<CoreMap> searchItemStatCode(); 
    
    public List<CoreMap> searchRealData(CoreMap param) throws Exception;
}
