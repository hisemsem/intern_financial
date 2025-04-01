//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.interest.controller;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.lon.api.interest.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/interests"})
public class InterestController extends CoreAbstractServiceImpl {
    private final InterestService interestService;

    @Autowired
    public InterestController(@Qualifier("bsApiInterestService") InterestService interestService) {
        this.interestService = interestService;
    }
}
