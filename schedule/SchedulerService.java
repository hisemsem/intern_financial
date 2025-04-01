//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.core.schedule;

import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.lon.api.interest.mapper.InterestMapper;
import com.skylark.lon.api.interest.service.InterestService;
import com.skylark.lon.app.cmm.biz.LonAppCmmService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("bsApiSchedulerService")
public class SchedulerService extends CoreAbstractServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private static String schedulerStatusRequest = "01";
    private static String schedulerStatusInsert = "02";
    private static String schedulerStatusError = "03";
    private static String schedulerStatusSuccess = "04";
    @Resource(
        name = "bsLonAppCmmService"
    )
    private LonAppCmmService comService;
    @Resource(
        name = "bmApiInterestMapper"
    )
    private InterestMapper interestMapper;
    @Autowired
    private InterestService interestService;

    public SchedulerService() {
    }

    public String getSchedulerStatusRequest() {
        return schedulerStatusRequest;
    }

    public String getSchedulerStatusInsert() {
        return schedulerStatusInsert;
    }

    public String getSchedulerStatusError() {
        return schedulerStatusError;
    }

    public String getSchedulerStatusSuccess() {
        return schedulerStatusSuccess;
    }

    @Async
    public void executeMasterTask(CoreMap masterParam) throws Exception {
        this.interestService.LONAPIEXT00221(masterParam);
    }

    @Async
    public void executeItemTask(CoreMap itemParam) throws Exception {
        this.interestService.LONAPIEXT00222(itemParam);
    }

    @Async
    public void executeDataCycleYTask(CoreMap dataParam) throws Exception {
        this.interestService.LONAPICCY00223(dataParam);
    }

    @Async
    public void executeDataCycleMTask(CoreMap dataParam) throws Exception {
        this.interestService.LONAPICCM00223(dataParam);
    }

    @Async
    public void executeDataCycleDTask(CoreMap dataParam) throws Exception {
        this.interestService.LONAPICCD00223(dataParam);
    }

    @Async
    public void executeDataCycleQTask(CoreMap dataParam) throws Exception {
        this.interestService.LONAPICCQ00223(dataParam);
    }

    public void executeMasterScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            this.executeMasterTask(param);
        } catch (Exception var5) {
            LOGGER.error("Error executing scheduled task", var5);
        }

    }

    public void executeItemScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            this.executeItemTask(param);
        } catch (Exception var5) {
            LOGGER.error("Error executing scheduled task", var5);
        }

    }

    public void executeDataCycleYScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            String schedulerCycleY = SchedulerSetup.getSchedulerCycleY();
            SchedulerSetup.setDatesBasedOnCycle("A");
            String schedulerStartDate = SchedulerSetup.getSchedulerStartDate();
            String schedulerEndDate = SchedulerSetup.getSchedulerEndDate();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            param.put("cycle", schedulerCycleY);
            param.put("startDate", schedulerStartDate);
            param.put("endDate", schedulerEndDate);
            this.executeDataCycleYTask(param);
        } catch (Exception var8) {
            LOGGER.error("Error executing scheduled task", var8);
        }

    }

    public void executeDataCycleMScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            String schedulerCycleM = SchedulerSetup.getSchedulerCycleM();
            SchedulerSetup.setDatesBasedOnCycle("M");
            String schedulerStartDate = SchedulerSetup.getSchedulerStartDate();
            String schedulerEndDate = SchedulerSetup.getSchedulerEndDate();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            param.put("cycle", schedulerCycleM);
            param.put("startDate", schedulerStartDate);
            param.put("endDate", schedulerEndDate);
            this.executeDataCycleMTask(param);
        } catch (Exception var8) {
            LOGGER.error("Error executing scheduled task", var8);
        }

    }

    public void executeDataCycleDScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            String schedulerCycleD = SchedulerSetup.getSchedulerCycleD();
            SchedulerSetup.setDatesBasedOnCycle("D");
            String schedulerStartDate = SchedulerSetup.getSchedulerStartDate();
            String schedulerEndDate = SchedulerSetup.getSchedulerEndDate();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            param.put("cycle", schedulerCycleD);
            param.put("startDate", schedulerStartDate);
            param.put("endDate", schedulerEndDate);
            this.executeDataCycleDTask(param);
        } catch (Exception var8) {
            LOGGER.error("Error executing scheduled task", var8);
        }

    }

    public void executeDataCycleQScheduledTask() {
        try {
            String schedulerLanguageType = SchedulerSetup.getSchedulerLanguageType();
            String schedulerStartCount = SchedulerSetup.getSchedulerStartCount();
            String schedulerEndCount = SchedulerSetup.getSchedulerEndCount();
            String schedulerCycleQ = SchedulerSetup.getSchedulerCycleQ();
            SchedulerSetup.setDatesBasedOnCycle("Q");
            String schedulerStartDate = SchedulerSetup.getSchedulerStartDate();
            String schedulerEndDate = SchedulerSetup.getSchedulerEndDate();
            CoreMap param = new CoreMap();
            param.put("languageType", schedulerLanguageType);
            param.put("startCount", schedulerStartCount);
            param.put("endCount", schedulerEndCount);
            param.put("cycle", schedulerCycleQ);
            param.put("startDate", schedulerStartDate);
            param.put("endDate", schedulerEndDate);
            this.executeDataCycleQTask(param);
        } catch (Exception var8) {
            LOGGER.error("Error executing scheduled task", var8);
        }

    }
}
