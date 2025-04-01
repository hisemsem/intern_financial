package com.skylark.lon.api.interest.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.lon.api.interest.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
public class InterestController extends CoreAbstractServiceImpl {

    private final InterestService interestService;

    @Autowired
    public InterestController (@Qualifier("bsApiInterestService") InterestService interestService) {
        this.interestService = interestService;
    }

    // Command Endpoint - 데이터 갱신
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExchangeRate (@PathVariable String id, @RequestBody CoreMap data) {
        interestService.updateInterest(id, data);
        return ResponseEntity.ok().build();
    }

    // Query Endpoint - 데이터 조회
    @PostMapping("/test.do")
    public ResponseEntity<CoreMap> getInterest (@RequestParam CoreMap data) {
        List<CoreMap> interests = interestService.getInterest(data); // 고쳐야함
        return ResponseEntity.ok((CoreMap) interests);
    }

    
    

}
