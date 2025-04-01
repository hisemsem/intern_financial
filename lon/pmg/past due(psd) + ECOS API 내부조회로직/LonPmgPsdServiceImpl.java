package com.skylark.lon.pmg.psd.biz;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.property.biz.CoreMessageSource;
import com.skylark.lon.api.gateway.GatewayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("bsLonPmgPsdService")
public class LonPmgPsdServiceImpl extends CoreAbstractServiceImpl implements LonPmgPsdService {

	/** Service & Resource call area */
	@Resource(name = "bsCoreMessageSource")
    private CoreMessageSource messageSource;

    @Resource(name = "bmLonPmgPsdMapper")
    private LonPmgPsdMapper lonPmgPsdMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(LonPmgPsdServiceImpl.class);
    
    //todo : 주석친 부분 나중에 아래 로직 구현및 수정해야함!!
    @Autowired
    private GatewayController gatewayController;

    /**
     * Lon Past Due Search (조회)
     */
    @Override
	public List<CoreMap> LONPMGPSD002(CoreMap param) throws Exception {
		// TODO Auto-generated method stub
		return lonPmgPsdMapper.selectListSearchLonPmgPsd(param);
	}
    
    /**
     * API 테스트용 : 테스트종료후 코드 삭제!!
     */
    @Override
	public List<CoreMap> LONPMGTST00222(CoreMap param) throws Exception {

        LOGGER.debug(String.valueOf(param));
        Object interestmodel = gatewayController.getInterest(param);

        LOGGER.debug("여기요여기"+interestmodel.toString());
    	return lonPmgPsdMapper.selectListSearchFrmAppEac(param);
	
    }
	
    //[306010] Master Api Search
    @Override
    public List<CoreMap> LONAPITST00221 (CoreMap param) throws Exception {
    	 return lonPmgPsdMapper.searchMasterLonApiTst(param);
    
    } 
    
  //[306010] Data Api Search
    @Override
    public List<CoreMap> LONAPITST00222 (CoreMap param) throws Exception {
    	 return lonPmgPsdMapper.searchDataLonApiTst(param);
    
    } 
    
    //[306010] Item Api Search
    @Override
    public List<CoreMap> LONAPITST00223 (CoreMap param) throws Exception {
    	 return lonPmgPsdMapper.searchItemLonApiTst(param);
    
    } 
}


