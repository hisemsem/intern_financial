//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.interest.service;

import com.skylark.frm.core.map.CoreMap;
import java.sql.Timestamp;
import java.util.List;

public interface InterestService {
    List<CoreMap> LONPMGTST00222(CoreMap var1) throws Exception;

    List<CoreMap> LONPMGTST00221(CoreMap var1) throws Exception;

    void LONPMGTST009(CoreMap var1) throws Exception;

    void LONPMGTST019(CoreMap var1) throws Exception;

    void storeStatData(List<CoreMap> var1) throws Exception;

    CoreMap LONAPIEXT00221(CoreMap var1) throws Exception;

    CoreMap LONAPIEXT00222(CoreMap var1) throws Exception;

    CoreMap LONAPICCY00223(CoreMap var1);

    CoreMap LONAPICCM00223(CoreMap var1);

    CoreMap LONAPICCD00223(CoreMap var1);

    CoreMap LONAPICCQ00223(CoreMap var1);

    void deleteOriginalMaster();

    void deleteOriginalItem();

    void deleteOriginalData();

    List<CoreMap> LONAPITST00222(CoreMap var1) throws Exception;

    CoreMap insertNewMaster(CoreMap var1, List<CoreMap> var2);

    CoreMap insertNewItem(CoreMap var1, List<CoreMap> var2);

    CoreMap insertNewData(CoreMap var1, List<CoreMap> var2, String var3, CoreMap var4);

    CoreMap apiRequestManagementParam(String var1, int var2, String var3, String var4, String var5);

    CoreMap apiRequestHistoryParam(String var1, String var2, String var3, String var4, Timestamp var5, Timestamp var6, String var7, String var8, String var9);

    void insertEcosSchedulerManagement(String var1, int var2, String var3, String var4, String var5);

    void insertEcosSchedulerHistory(String var1, String var2, String var3, String var4, Timestamp var5, Timestamp var6, String var7, String var8, String var9);

    void updateEcosSchedulerManagement(String var1, int var2, String var3, String var4, String var5);

    void updateEcosSchedulerHistory(String var1, String var2, String var3, String var4, Timestamp var5, Timestamp var6, String var7, String var8, String var9);
}
