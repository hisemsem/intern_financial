package com.skylark.lon.pmg.psd.biz;

import com.skylark.frm.core.map.CoreMap;

import java.util.List;

public interface LonPmgPsdService {

	 /**
     * Lon Past Due Search (조회)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public List<CoreMap> LONPMGPSD002(CoreMap param) throws Exception;

	/**
	 * API 테스트용 : 테스트종료후 코드 삭제!!
	 */
	public List<CoreMap> LONPMGTST00222(CoreMap param) throws Exception;
   
	  //[306010] Master Api Search
	public List<CoreMap> LONAPITST00221 (CoreMap param) throws Exception;
    
  //[306010] Data Api Search
	public List<CoreMap> LONAPITST00222 (CoreMap param) throws Exception;
    
  //[306010] Item Api Search
	public List<CoreMap> LONAPITST00223 (CoreMap param) throws Exception;
	
}


