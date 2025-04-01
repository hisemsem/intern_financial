package com.skylark.lon.pmg.pdr.biz;

import com.skylark.frm.core.map.CoreMap;

import java.util.List;

public interface LonPmgPdrService {

	 /**
     * Lon Past Due Recode Search (조회)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public List<CoreMap> LONPMGPDR002(CoreMap param) throws Exception;
   
	
}
