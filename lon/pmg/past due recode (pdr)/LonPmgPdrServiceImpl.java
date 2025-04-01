package com.skylark.lon.pmg.pdr.biz;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.property.biz.CoreMessageSource;

@Service("bsLonPmgPdrService")
public class LonPmgPdrServiceImpl extends CoreAbstractServiceImpl implements LonPmgPdrService {

	/** Service & Resource call area */
	@Resource(name = "bsCoreMessageSource")
    private CoreMessageSource messageSource;

    @Resource(name = "bmLonPmgPdrMapper")
    private LonPmgPdrMapper lonPmgPdrMapper;
    
    /**
     * Lon Past Due Record Search (조회)
     */
    @Override
	public List<CoreMap> LONPMGPDR002(CoreMap param) throws Exception {
    	return lonPmgPdrMapper.selectListSearchLonPmgPdr(param);
	}
	
	
}
