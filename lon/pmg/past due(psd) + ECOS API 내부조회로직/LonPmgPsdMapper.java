package com.skylark.lon.pmg.psd.biz;

import java.util.List;

import com.skylark.frm.core.annotation.CoreMapper;
import com.skylark.frm.core.map.CoreMap;

@CoreMapper("bmLonPmgPsdMapper")
public interface LonPmgPsdMapper {

    /*
     * ==================================================================== Employee
     * Management
     * ====================================================================
     * selectListFrmBapEmp : Employee Management (조회) selectDetailFrmBapEmp :
     * Employee Management (상세조회) insertFrmBapEmp : Employee Management (등록)
     * updateFrmBapEmp : Employee Management (수정) deleteFrmBapEmp : Employee
     * Management (삭제)
     * selectListFrmBapEmpJobPosition: Employee Management Job Position (조회)
     * ====================================================================
     */

    /**
     * Lon Past Due Search (조회)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public List<CoreMap> selectListSearchLonPmgPsd(CoreMap param) throws Exception;
    
    //102070화면 조회 테스트
    public List<CoreMap> selectListSearchFrmAppEac(CoreMap param) throws Exception;
    
    //306010 Master Table 조회 테스트
    public List<CoreMap> searchMasterLonApiTst (CoreMap param) throws Exception;
    
  //306010 Data Table 조회 테스트
    public List<CoreMap> searchDataLonApiTst (CoreMap param) throws Exception;
    
  //306010 Item Table 조회 테스트
    public List<CoreMap> searchItemLonApiTst (CoreMap param) throws Exception;
}

