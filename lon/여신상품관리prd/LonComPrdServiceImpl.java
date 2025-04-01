/*
 * Copyright (c) 1994, 2013, IMB System and/or its affiliates. All rights reserved.
 * IMB System PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skylark.lon.com.prd.biz;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.skylark.frm.bap.com.biz.FrmBapComService;
import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.constant.CoreAppConstant;
import com.skylark.frm.core.constant.CoreGlobals;
import com.skylark.frm.core.constant.CoreAppConstant.COMMON;
import com.skylark.frm.core.exception.CoreBizException;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.property.biz.CoreMessageSource;
import com.skylark.frm.core.util.CoreDateUtil;
import com.skylark.lon.app.cmm.biz.LonAppCmmService;
import com.skylark.lon.hlp.cmm.biz.LonHlpCmmMapper;
import com.skylark.lon.org.apl.biz.LonOrgAplMapper;

/* ====================================================================
 * Program            : 담보 설정 관리
 * Description        : 담보 설정 관리 (조회/등록/수정/삭제)
 *
 * Create Date        :
 * Create Author      :
 * ====================================================================
 * Modify Sequence    :
 * Modify Date        :
 * Modify Author      :
 * Modify Description :
 * Modify Search Key  :
 * ====================================================================
 * Reference
 * ====================================================================
 * 클래스 이름은 해당 최상위 패키지 이름의 약어로 시작함
 * 클래스, 변수, 함수의 주석은 Shift+Alt+j 로 생성후 작성함
 * import 추가는 직접하지 말고 Shift+Ctrl+o 로 자동 정리함
 * ====================================================================
 */

@Service("bsLonComPrdService")
public class LonComPrdServiceImpl extends CoreAbstractServiceImpl implements LonComPrdService {

    @Resource(name = "bsCoreMessageSource")
    private CoreMessageSource messageSource;
    
    @Resource(name = "bsLonAppCmmService")
    private LonAppCmmService comService;

    @Resource(name = "bmLonComPrdMapper")
    private LonComPrdMapper lonComPrdMapper;
    
    @Resource(name = "bmLonOrgAplMapper")
    private LonOrgAplMapper lonOrgAplMapper;
    
    @Resource(name = "bmLonHlpCmmMapper")
    private LonHlpCmmMapper mapper;
    
    /**
     * Lon Product Search (조회)
     */
    @Override
    public List<CoreMap> LONCOMPRD002(CoreMap param) throws Exception {
        return lonComPrdMapper.selectListSearchLonComPrd(param);
    }
    
    /**
     * Lon Product Management (등록/수정/삭제)
     */
    @Override
    public void LONCOMPRD009(CoreMap param) throws Exception {
        param.put("sysDatetime", CoreDateUtil.getCurrentDateTime());
        if (ObjectUtils.isEmpty(param.get("sysOrganizationCode"))) {
            param.put("sysOrganizationCode", CoreGlobals.ORGANIZATION_CODE);
        }
        if (ObjectUtils.isEmpty(param.get("sysUserId"))) {
            param.put("sysUserId", getSysUserId());
        }
        String transactionMode = (String) param.get("transactionMode");
        
        if ("NEW".equals(transactionMode)) {
            lonComPrdMapper.insertProductLonComPrd(param);
        } else if ("UPDATE".equals(transactionMode)) {
        	lonComPrdMapper.updateProductLonComPrd(param);
        } else if ("DELETE".equals(transactionMode)) {
        	lonComPrdMapper.deleteProductLonComPrd(param);
        }
    }
    
    
    
}
