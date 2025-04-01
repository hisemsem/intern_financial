/*
 * Copyright (c) 1994, 2013, IMB System and/or its affiliates. All rights reserved.
 * IMB System PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skylark.lon.com.prd.biz;

import java.util.List;

import com.skylark.frm.core.map.CoreMap;

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

public interface LonComPrdService {
    
    /**
     * Lon Product Search (조회)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public List<CoreMap> LONCOMPRD002(CoreMap param) throws Exception;
    
    /**
     * Lon Product Manage (등록/수정/삭제)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public void LONCOMPRD009(CoreMap param) throws Exception;
   
}
