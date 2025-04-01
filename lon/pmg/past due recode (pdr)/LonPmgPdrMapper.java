package com.skylark.lon.pmg.pdr.biz;

import java.util.List;

import com.skylark.frm.core.annotation.CoreMapper;
import com.skylark.frm.core.map.CoreMap;

@CoreMapper("bmLonPmgPdrMapper")
public interface LonPmgPdrMapper {

    /*
     * ==================================================================== 
     * Post Management
     * ====================================================================
     * selectListSearchLonPmgPdr : Lon Past Due Record Search(조회) 
     * ====================================================================
     */

    /**
     * Lon Past Due Record Search (조회)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public List<CoreMap> selectListSearchLonPmgPdr(CoreMap param) throws Exception;
    
}
