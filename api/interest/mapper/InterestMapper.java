//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.interest.mapper;

import com.skylark.frm.core.annotation.CoreMapper;
import com.skylark.frm.core.map.CoreMap;
import java.util.List;

@CoreMapper("bmApiInterestMapper")
public interface InterestMapper {
    void insertExternalMasterDataApi(CoreMap var1);

    void insertExternalItemDataApi(CoreMap var1);

    void insertExternalStatDataApi(CoreMap var1);

    List<CoreMap> searchMasterFrmAppEac(CoreMap var1) throws Exception;

    List<CoreMap> searchItemFrmAppEac(CoreMap var1) throws Exception;

    void updateMasterFrmAppEac(CoreMap var1) throws Exception;

    void updateItemFrmAppEac(CoreMap var1) throws Exception;

    List<CoreMap> searchOriginalMaster();

    List<CoreMap> searchOriginalItem();

    List<CoreMap> searchOriginalData();

    List<CoreMap> searchMatchedOriginalData(CoreMap var1);

    void deleteOriginalMaster();

    void deleteOriginalItem();

    void deleteOriginalData();

    void deleteDataSearchItemCode(String var1);

    void insertNewMasterData(CoreMap var1);

    void insertNewItemListData(CoreMap var1);

    void insertNewRealData(CoreMap var1);

    List<CoreMap> searchMasterUsedFlag();

    List<CoreMap> searchRealData(CoreMap var1) throws Exception;

    List<CoreMap> searchUsingCodeListWithCycle(String var1);

    void insertEcosSchedulerManagement(CoreMap var1);

    void insertEcosSchedulerHistory(CoreMap var1);

    void updateEcosSchedulerManagement(CoreMap var1);

    void updateEcosSchedulerHistory(CoreMap var1);
}
