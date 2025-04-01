//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.interest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.util.CoreJsonUtil;
import com.skylark.lon.api.interest.external.controller.ExternalApiController;
import com.skylark.lon.api.interest.mapper.InterestMapper;
import com.skylark.lon.api.util.DateFormatUtil;
import com.skylark.lon.app.cmm.biz.LonAppCmmService;
import com.skylark.lon.core.schedule.SchedulerService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bsApiInterestService")
public class InterestServiceImpl extends CoreAbstractServiceImpl implements InterestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterestServiceImpl.class);
    @Resource(
        name = "bmApiInterestMapper"
    )
    private InterestMapper interestMapper;
    @Resource(
        name = "bsLonAppCmmService"
    )
    private LonAppCmmService comService;
    @Autowired
    private SchedulerService schedulerService;
    private final ExternalApiController externalApiController;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    public InterestServiceImpl(ExternalApiController externalApiController) {
        this.externalApiController = externalApiController;
    }

    public List<CoreMap> LONPMGTST00222(CoreMap param) throws Exception {
        new CoreMap();
        return this.interestMapper.searchMasterFrmAppEac(param);
    }

    public List<CoreMap> LONPMGTST00221(CoreMap param) throws Exception {
        return this.interestMapper.searchItemFrmAppEac(param);
    }

    public void LONPMGTST009(CoreMap param) throws Exception {
        if (!param.get("datas").equals("")) {
            List<CoreMap> datas = (List)param.get("datas");
            Iterator var4 = datas.iterator();

            while(var4.hasNext()) {
                CoreMap data = (CoreMap)var4.next();
                data.put("userId", this.getSysUserId());
                if (data.get("statCode") != null && !data.get("statCode").equals("")) {
                    this.interestMapper.updateMasterFrmAppEac(data);
                }
            }
        }

    }

    public void LONPMGTST019(CoreMap param) throws Exception {
        if (!param.get("datas").equals("")) {
            List<CoreMap> datas = (List)param.get("datas");
            Iterator var4 = datas.iterator();

            while(var4.hasNext()) {
                CoreMap data = (CoreMap)var4.next();
                data.put("userId", this.getSysUserId());
                String itemId = data.get("statCode").toString() + data.get("itemCode").toString() + data.get("cycle").toString();
                if (data.get("statCode") != null && !data.get("statCode").equals("")) {
                    data.put("itemId", itemId);
                    this.interestMapper.updateItemFrmAppEac(data);
                }

                if (data.get("detailUsedFlag").equals("0")) {
                    this.interestMapper.deleteDataSearchItemCode(itemId);
                }
            }

            this.interestMapper.searchOriginalItem();
        }

    }

    public CoreMap apiRequestHistoryParam(String historyId, String requestId, String itemId, String requestStatusDesc, Timestamp requestDtm, Timestamp responseDtm, String errorCode, String errorMessage, String requestStatus) {
        CoreMap param = new CoreMap();
        param.put("historyId", historyId);
        param.put("requestId", requestId);
        param.put("itemId", itemId);
        param.put("requestStatusDesc", requestStatusDesc);
        param.put("requestDtm", requestDtm);
        param.put("responseDtm", responseDtm);
        param.put("errorCode", errorCode);
        param.put("errorMessage", errorMessage);
        param.put("requestStatus", requestStatus);
        return param;
    }

    public CoreMap apiRequestManagementParam(String itemId, int retryCnt, String usedFlag, String requestStatus, String requestId) {
        CoreMap param = new CoreMap();
        param.put("itemId", itemId);
        param.put("retryCnt", retryCnt);
        param.put("usedFlag", usedFlag);
        param.put("requestStatus", requestStatus);
        param.put("requestId", requestId);
        return param;
    }

    public void insertEcosSchedulerManagement(String itemId, int retryCnt, String usedFlag, String requestStatus, String requestId) {
        new CoreMap();
        CoreMap managementParam = this.apiRequestManagementParam(itemId, retryCnt, usedFlag, requestStatus, requestId);
        this.interestMapper.insertEcosSchedulerManagement(managementParam);
    }

    public void updateEcosSchedulerManagement(String itemId, int retryCnt, String usedFlag, String requestStatus, String requestId) {
        new CoreMap();
        CoreMap managementParam = this.apiRequestManagementParam(itemId, retryCnt, usedFlag, requestStatus, requestId);
        this.interestMapper.updateEcosSchedulerManagement(managementParam);
    }

    public void updateEcosSchedulerHistory(String historyId, String requestId, String itemId, String requestStatusDesc, Timestamp requestDtm, Timestamp responseDtm, String errorCode, String errorMessage, String requestStatus) {
        new CoreMap();
        CoreMap historyParam = this.apiRequestHistoryParam(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
        this.interestMapper.updateEcosSchedulerHistory(historyParam);
    }

    public void insertEcosSchedulerHistory(String historyId, String requestId, String itemId, String requestStatusDesc, Timestamp requestDtm, Timestamp responseDtm, String errorCode, String errorMessage, String requestStatus) {
        new CoreMap();
        CoreMap historyParam = this.apiRequestHistoryParam(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
        this.interestMapper.insertEcosSchedulerHistory(historyParam);
    }

    public CoreMap LONAPIEXT00221(CoreMap data) throws Exception {
        String itemId = "";
        int retryCnt = 0;
        String usedFlag = "Y";
        String requestStatus = "";
        String requestId = this.comService.getEcosSchedulerNo();
        String historyId = this.comService.getEcosSchedulerDetailNo();
        new Timestamp(System.currentTimeMillis());
        new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String errorMessage = "";
        String errorCode = "";
        CoreMap result = new CoreMap();

        Timestamp requestDtm;
        Timestamp responseDtm;
        try {
            String languageType = (String)data.get("languageType");
            String startCount = (String)data.get("startCount");
            String endCount = (String)data.get("endCount");
            requestStatus = this.schedulerService.getSchedulerStatusRequest();
            this.insertEcosSchedulerManagement(itemId, retryCnt, usedFlag, requestStatus, requestId);
            requestDtm = new Timestamp(System.currentTimeMillis());
            String resultString = (String)this.externalApiController.fetchStatTableList(languageType, startCount, endCount).block();
            responseDtm = new Timestamp(System.currentTimeMillis());
            requestStatusDesc = "Master Scheduler request";
            this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
            LOGGER.debug("Result from external API: {}", resultString);
            result.put("statTableList", resultString);
            requestStatus = this.schedulerService.getSchedulerStatusInsert();
            this.updateEcosSchedulerManagement(itemId, retryCnt, usedFlag, requestStatus, requestId);
            requestDtm = new Timestamp(System.currentTimeMillis());
            List<CoreMap> originResult = this.interestMapper.searchOriginalMaster();
            this.deleteOriginalMaster();
            this.interestMapper.insertNewMasterData(this.insertNewMaster(result, originResult));
            responseDtm = new Timestamp(System.currentTimeMillis());
            requestStatusDesc = "Master data insert";
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
            LOGGER.debug("data insert");
        } catch (Exception var19) {
            errorMessage = "Error fetching data from external API";
            requestDtm = new Timestamp(System.currentTimeMillis());
            requestStatus = this.schedulerService.getSchedulerStatusError();
            this.updateEcosSchedulerManagement(itemId, retryCnt, usedFlag, requestStatus, requestId);
            requestStatusDesc = "Master Scheduler error";
            responseDtm = new Timestamp(System.currentTimeMillis());
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
            result.put("error", var19.getMessage());
            return result;
        }

        requestDtm = new Timestamp(System.currentTimeMillis());
        requestStatus = this.schedulerService.getSchedulerStatusSuccess();
        this.updateEcosSchedulerManagement(itemId, retryCnt, usedFlag, requestStatus, requestId);
        responseDtm = new Timestamp(System.currentTimeMillis());
        requestStatusDesc = "Master Scheduler success";
        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
        return result;
    }

    public CoreMap LONAPIEXT00222(CoreMap data) throws Exception {
        String itemId = "";
        int retryCnt = 0;
        String newUsedFlag = "Y";
        String requestStatus = "";
        new Timestamp(System.currentTimeMillis());
        new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String errorMessage = "";
        String errorCode = "";
        String requestId = "";
        String historyId = "";
        CoreMap result = new CoreMap();
        Map<String, String> requestIdMap = new HashMap();
        Map<String, String> historyIdMap = new HashMap();
        List<CoreMap> masterDataList = this.interestMapper.searchMasterUsedFlag();
        List<String> statCodes = new ArrayList();

        Timestamp requestDtm;
        Timestamp responseDtm;
        String statCode;
        Iterator var20;
        Iterator var28;
        try {
            var28 = masterDataList.iterator();

            String statCode;
            while(var28.hasNext()) {
                CoreMap record = (CoreMap)var28.next();
                Object usedFlag = record.get("usedFlag");
                if (usedFlag != null && usedFlag.equals("1")) {
                    statCode = (String)record.get("statCode");
                    if (statCode != null) {
                        statCodes.add(statCode);
                    }
                }
            }

            List<String> statItemList = new ArrayList();
            var20 = statCodes.iterator();

            while(var20.hasNext()) {
                statCode = (String)var20.next();
                requestId = this.comService.getEcosSchedulerNo();
                historyId = this.comService.getEcosSchedulerDetailNo();
                requestIdMap.put(statCode, requestId);
                historyIdMap.put(statCode, historyId);
                statCode = (String)data.get("languageType");
                String startCount = (String)data.get("startCount");
                String endCount = (String)data.get("endCount");
                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                this.insertEcosSchedulerManagement(statCode, retryCnt, newUsedFlag, requestStatus, requestId);
                requestDtm = new Timestamp(System.currentTimeMillis());
                requestStatusDesc = "Master Scheduler request";
                String resultString = (String)this.externalApiController.fetchItemTableList(statCode, startCount, endCount, statCode).block();
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.insertEcosSchedulerHistory(historyId, requestId, statCode, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
                statItemList.add(resultString);
            }

            result.put("statItemList", statItemList);
            List<CoreMap> originResult = this.interestMapper.searchOriginalItem();
            this.deleteOriginalItem();
            requestStatus = this.schedulerService.getSchedulerStatusInsert();
            Iterator var33 = statCodes.iterator();

            while(var33.hasNext()) {
                String statCode = (String)var33.next();
                requestId = (String)requestIdMap.get(statCode);
                requestStatus = this.schedulerService.getSchedulerStatusInsert();
                this.updateEcosSchedulerManagement(statCode, retryCnt, newUsedFlag, requestStatus, requestId);
                LOGGER.debug("data insert");
            }

            requestDtm = new Timestamp(System.currentTimeMillis());
            CoreMap insertNewItemData = this.insertNewItem(result, originResult);
            this.interestMapper.insertNewItemListData(insertNewItemData);
            responseDtm = new Timestamp(System.currentTimeMillis());
            Iterator var34 = statCodes.iterator();

            while(var34.hasNext()) {
                statCode = (String)var34.next();
                requestId = (String)requestIdMap.get(statCode);
                historyId = (String)historyIdMap.get(statCode);
                requestStatus = this.schedulerService.getSchedulerStatusInsert();
                this.updateEcosSchedulerHistory(historyId, requestId, statCode, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
            }
        } catch (Exception var25) {
            requestDtm = new Timestamp(System.currentTimeMillis());
            var20 = statCodes.iterator();

            while(var20.hasNext()) {
                statCode = (String)var20.next();
                requestId = (String)requestIdMap.get(statCode);
                historyId = (String)historyIdMap.get(statCode);
                requestStatus = this.schedulerService.getSchedulerStatusError();
                this.updateEcosSchedulerManagement(statCode, retryCnt, newUsedFlag, requestStatus, requestId);
                requestStatusDesc = "Item Scheduler error";
                errorMessage = "Error fetching data from external API";
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.updateEcosSchedulerHistory(historyId, requestId, statCode, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
            }

            result.put("error", var25.getMessage());
            return result;
        }

        requestDtm = new Timestamp(System.currentTimeMillis());
        requestStatus = this.schedulerService.getSchedulerStatusSuccess();
        requestStatusDesc = "Item Scheduler success";
        responseDtm = new Timestamp(System.currentTimeMillis());
        var28 = statCodes.iterator();

        while(var28.hasNext()) {
            String statCode = (String)var28.next();
            requestId = (String)requestIdMap.get(statCode);
            historyId = (String)historyIdMap.get(statCode);
            this.updateEcosSchedulerManagement(statCode, retryCnt, newUsedFlag, requestStatus, requestId);
            this.updateEcosSchedulerHistory(historyId, requestId, statCode, requestStatusDesc, requestDtm, responseDtm, errorCode, errorMessage, requestStatus);
        }

        return result;
    }

    public CoreMap LONAPICCY00223(CoreMap data) {
        String itemId = "";
        int retryCnt = 0;
        String newUsedFlag = "Y";
        String requestStatus = "";
        Timestamp requestDtm = new Timestamp(System.currentTimeMillis());
        new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String newErrorMessage = "";
        String newErrorCode = "";
        String requestId = "";
        String historyId = "";
        CoreMap result = new CoreMap();
        CoreMap deletedExternalData = new CoreMap();
        List<CoreMap> errorExternalDataList = new ArrayList();
        int MAX_RETRIES = true;

        Timestamp responseDtm;
        try {
            List<CoreMap> usingCodeList = this.interestMapper.searchUsingCodeListWithCycle("A");
            Iterator var19 = usingCodeList.iterator();

            CoreMap errorCode;
            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                String statCode = (String)errorCode.get("statCode");
                String itemCode1 = (String)errorCode.get("itemCode1");
                String cycle = (String)data.get("cycle");
                String languageType = (String)data.get("languageType");
                String startDate = (String)data.get("startDate");
                String endDate = (String)data.get("endDate");
                String startCount = (String)data.get("startCount");
                String endCount = (String)data.get("endCount");
                itemId = statCode + itemCode1 + cycle;

                boolean retry;
                String errorMessage;
                do {
                    retry = false;
                    retryCnt = 0;

                    try {
                        String resultMessage;
                        label101: {
                            label100: {
                                requestId = this.comService.getEcosSchedulerNo();
                                historyId = this.comService.getEcosSchedulerDetailNo();
                                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                                this.insertEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                requestDtm = new Timestamp(System.currentTimeMillis());
                                String resultString = (String)this.externalApiController.fetchDataTableList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode1).block();
                                responseDtm = new Timestamp(System.currentTimeMillis());
                                requestStatusDesc = "Master Scheduler request";
                                this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                                LOGGER.debug("Result from external API: {}", resultString);
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode rootNode = objectMapper.readTree(resultString);
                                errorMessage = rootNode.path("RESULT").path("CODE").asText();
                                resultMessage = rootNode.path("RESULT").path("MESSAGE").asText();
                                switch (errorMessage) {
                                    case "ERROR-100":
                                    case "ERROR-101":
                                    case "ERROR-200":
                                    case "ERROR-300":
                                    case "ERROR-301":
                                    case "ERROR-400":
                                    case "ERROR-500":
                                    case "ERROR-600":
                                    case "ERROR-601":
                                    case "ERROR-602":
                                    case "INFO-100":
                                    case "INFO-200":
                                        requestStatus = this.schedulerService.getSchedulerStatusError();
                                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                        requestStatusDesc = "Data(Y) Scheduler error";
                                        newErrorCode = errorMessage;
                                        newErrorMessage = resultMessage;
                                        responseDtm = new Timestamp(System.currentTimeMillis());
                                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                                        continue;
                                }

                                result.put("statTableList", resultString);
                                continue;
                            }

                            ++retryCnt;
                            if (retryCnt < 3) {
                                retry = true;
                                LOGGER.debug("Retry attempt {} for statCode: {}, itemCode1: {}, cycle: {}", new Object[]{retryCnt, statCode, itemCode1, cycle});
                            } else {
                                CoreMap retryProblemResult = new CoreMap();
                                retryProblemResult.put("problemCode", itemCode1);
                                retryProblemResult.put("code", errorMessage);
                                retryProblemResult.put("message", "Max retries exceeded. " + resultMessage);
                                errorExternalDataList.add(retryProblemResult);
                            }
                            continue;
                        }

                        requestStatus = this.schedulerService.getSchedulerStatusError();
                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                        responseDtm = new Timestamp(System.currentTimeMillis());
                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                        return result;
                    } catch (Exception var36) {
                        if (retryCnt >= 3) {
                            throw var36;
                        }

                        retry = true;
                        LOGGER.debug("Exception on attempt {}: {}", retryCnt, var36.getMessage());
                    }
                } while(retry);

                List<CoreMap> originResult = this.interestMapper.searchOriginalData();
                String newCycle = (String)data.get("cycle");
                deletedExternalData = this.insertNewData(result, originResult, newCycle, data);
                if (deletedExternalData.containsKey("error")) {
                    String code = (String)deletedExternalData.get("code");
                    errorMessage = (String)deletedExternalData.get("error");
                    LOGGER.debug("code: " + code + "  message: " + errorMessage);
                }

                requestStatus = this.schedulerService.getSchedulerStatusSuccess();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                requestStatusDesc = "Data(Y) Scheduler success";
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                LOGGER.debug("data insert");
            }

            var19 = errorExternalDataList.iterator();

            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                LOGGER.debug(errorCode.get("problemCode") + ": " + errorCode.get("message"));
            }

            return deletedExternalData;
        } catch (Exception var37) {
            requestStatus = this.schedulerService.getSchedulerStatusError();
            this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
            requestStatusDesc = "Data(Y) Scheduler error";
            responseDtm = new Timestamp(System.currentTimeMillis());
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
            LOGGER.error("Error fetching data from external API", var37);
            result.put("error", var37.getMessage());
            return result;
        }
    }

    public CoreMap LONAPICCM00223(CoreMap data) {
        String itemId = "";
        int retryCnt = 0;
        String newUsedFlag = "Y";
        String requestStatus = "";
        Timestamp requestDtm = new Timestamp(System.currentTimeMillis());
        new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String newErrorMessage = "";
        String newErrorCode = "";
        String requestId = "";
        String historyId = "";
        CoreMap result = new CoreMap();
        CoreMap deletedExternalData = new CoreMap();
        List<CoreMap> errorExternalDataList = new ArrayList();
        int MAX_RETRIES = true;

        Timestamp responseDtm;
        try {
            List<CoreMap> usingCodeList = this.interestMapper.searchUsingCodeListWithCycle("M");
            Iterator var19 = usingCodeList.iterator();

            CoreMap errorCode;
            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                String statCode = (String)errorCode.get("statCode");
                String itemCode1 = (String)errorCode.get("itemCode1");
                String cycle = (String)data.get("cycle");
                String languageType = (String)data.get("languageType");
                String startDate = (String)data.get("startDate");
                String endDate = (String)data.get("endDate");
                String startCount = (String)data.get("startCount");
                String endCount = (String)data.get("endCount");
                itemId = statCode + itemCode1 + cycle;

                boolean retry;
                String errorMessage;
                do {
                    retry = false;
                    retryCnt = 0;

                    try {
                        String resultMessage;
                        label101: {
                            label100: {
                                requestId = this.comService.getEcosSchedulerNo();
                                historyId = this.comService.getEcosSchedulerDetailNo();
                                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                                this.insertEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                requestDtm = new Timestamp(System.currentTimeMillis());
                                String resultString = (String)this.externalApiController.fetchDataTableList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode1).block();
                                responseDtm = new Timestamp(System.currentTimeMillis());
                                requestStatusDesc = "Master Scheduler request";
                                this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                                LOGGER.debug("Result from external API: {}", resultString);
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode rootNode = objectMapper.readTree(resultString);
                                errorMessage = rootNode.path("RESULT").path("CODE").asText();
                                resultMessage = rootNode.path("RESULT").path("MESSAGE").asText();
                                switch (errorMessage) {
                                    case "ERROR-100":
                                    case "ERROR-101":
                                    case "ERROR-200":
                                    case "ERROR-300":
                                    case "ERROR-301":
                                    case "ERROR-400":
                                    case "ERROR-500":
                                    case "ERROR-600":
                                    case "ERROR-601":
                                    case "ERROR-602":
                                    case "INFO-100":
                                    case "INFO-200":
                                        requestStatus = this.schedulerService.getSchedulerStatusError();
                                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                        newErrorCode = errorMessage;
                                        newErrorMessage = resultMessage;
                                        responseDtm = new Timestamp(System.currentTimeMillis());
                                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                                        continue;
                                }

                                result.put("statTableList", resultString);
                                continue;
                            }

                            ++retryCnt;
                            if (retryCnt < 3) {
                                retry = true;
                                LOGGER.debug("Retry attempt {} for statCode: {}, itemCode1: {}, cycle: {}", new Object[]{retryCnt, statCode, itemCode1, cycle});
                            } else {
                                CoreMap retryProblemResult = new CoreMap();
                                retryProblemResult.put("problemCode", itemCode1);
                                retryProblemResult.put("code", errorMessage);
                                retryProblemResult.put("message", "Max retries exceeded. " + resultMessage);
                                errorExternalDataList.add(retryProblemResult);
                            }
                            continue;
                        }

                        requestStatus = this.schedulerService.getSchedulerStatusError();
                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                        requestStatusDesc = "Data(M) Scheduler error";
                        responseDtm = new Timestamp(System.currentTimeMillis());
                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                        return result;
                    } catch (Exception var36) {
                        if (retryCnt >= 3) {
                            throw var36;
                        }

                        retry = true;
                        LOGGER.debug("Exception on attempt {}: {}", retryCnt, var36.getMessage());
                    }
                } while(retry);

                List<CoreMap> originResult = this.interestMapper.searchOriginalData();
                String newCycle = (String)data.get("cycle");
                deletedExternalData = this.insertNewData(result, originResult, newCycle, data);
                if (deletedExternalData.containsKey("error")) {
                    String code = (String)deletedExternalData.get("code");
                    errorMessage = (String)deletedExternalData.get("error");
                    LOGGER.debug("code: " + code + "  message: " + errorMessage);
                }

                requestStatus = this.schedulerService.getSchedulerStatusSuccess();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                requestStatusDesc = "Data(M) Scheduler success";
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                LOGGER.debug("data insert");
            }

            var19 = errorExternalDataList.iterator();

            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                LOGGER.debug(errorCode.get("problemCode") + ": " + errorCode.get("message"));
            }

            return deletedExternalData;
        } catch (Exception var37) {
            requestStatus = this.schedulerService.getSchedulerStatusError();
            this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
            requestStatusDesc = "Data(M) Scheduler error";
            responseDtm = new Timestamp(System.currentTimeMillis());
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
            LOGGER.error("Error fetching data from external API", var37);
            result.put("error", var37.getMessage());
            return result;
        }
    }

    public CoreMap LONAPICCD00223(CoreMap data) {
        String itemId = "";
        int retryCnt = 0;
        String newUsedFlag = "Y";
        String requestStatus = "";
        Timestamp requestDtm = new Timestamp(System.currentTimeMillis());
        new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String newErrorMessage = "";
        String newErrorCode = "";
        String requestId = "";
        String historyId = "";
        CoreMap result = new CoreMap();
        CoreMap deletedExternalData = new CoreMap();
        List<CoreMap> errorExternalDataList = new ArrayList();
        int MAX_RETRIES = true;

        Timestamp responseDtm;
        try {
            List<CoreMap> usingCodeList = this.interestMapper.searchUsingCodeListWithCycle("D");
            Iterator var19 = usingCodeList.iterator();

            CoreMap errorCode;
            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                String statCode = (String)errorCode.get("statCode");
                String itemCode1 = (String)errorCode.get("itemCode1");
                String cycle = (String)data.get("cycle");
                String languageType = (String)data.get("languageType");
                String startDate = (String)data.get("startDate");
                String endDate = (String)data.get("endDate");
                String startCount = (String)data.get("startCount");
                String endCount = (String)data.get("endCount");
                itemId = statCode + itemCode1 + cycle;
                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);

                boolean retry;
                String errorMessage;
                do {
                    retry = false;
                    retryCnt = 0;

                    try {
                        String resultMessage;
                        label101: {
                            label100: {
                                requestId = this.comService.getEcosSchedulerNo();
                                historyId = this.comService.getEcosSchedulerDetailNo();
                                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                                this.insertEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                requestDtm = new Timestamp(System.currentTimeMillis());
                                String resultString = (String)this.externalApiController.fetchDataTableList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode1).block();
                                responseDtm = new Timestamp(System.currentTimeMillis());
                                requestStatusDesc = "Master Scheduler request";
                                this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                                LOGGER.debug("Result from external API: {}", resultString);
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode rootNode = objectMapper.readTree(resultString);
                                errorMessage = rootNode.path("RESULT").path("CODE").asText();
                                resultMessage = rootNode.path("RESULT").path("MESSAGE").asText();
                                switch (errorMessage) {
                                    case "ERROR-100":
                                    case "ERROR-101":
                                    case "ERROR-200":
                                    case "ERROR-300":
                                    case "ERROR-301":
                                    case "ERROR-400":
                                    case "ERROR-500":
                                    case "ERROR-600":
                                    case "ERROR-601":
                                    case "ERROR-602":
                                    case "INFO-100":
                                    case "INFO-200":
                                        requestStatus = this.schedulerService.getSchedulerStatusError();
                                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                        newErrorCode = errorMessage;
                                        newErrorMessage = resultMessage;
                                        responseDtm = new Timestamp(System.currentTimeMillis());
                                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                                        continue;
                                }

                                result.put("statTableList", resultString);
                                continue;
                            }

                            ++retryCnt;
                            if (retryCnt < 3) {
                                retry = true;
                                LOGGER.debug("Retry attempt {} for statCode: {}, itemCode1: {}, cycle: {}", new Object[]{retryCnt, statCode, itemCode1, cycle});
                            } else {
                                CoreMap retryProblemResult = new CoreMap();
                                retryProblemResult.put("problemCode", itemCode1);
                                retryProblemResult.put("code", errorMessage);
                                retryProblemResult.put("message", "Max retries exceeded. " + resultMessage);
                                errorExternalDataList.add(retryProblemResult);
                            }
                            continue;
                        }

                        requestStatus = this.schedulerService.getSchedulerStatusError();
                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                        requestStatusDesc = "Data(D) Scheduler error";
                        responseDtm = new Timestamp(System.currentTimeMillis());
                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                        return result;
                    } catch (Exception var36) {
                        if (retryCnt >= 3) {
                            throw var36;
                        }

                        retry = true;
                        LOGGER.debug("Exception on attempt {}: {}", retryCnt, var36.getMessage());
                    }
                } while(retry);

                List<CoreMap> originResult = this.interestMapper.searchOriginalData();
                String newCycle = (String)data.get("cycle");
                deletedExternalData = this.insertNewData(result, originResult, newCycle, data);
                if (deletedExternalData.containsKey("error")) {
                    String code = (String)deletedExternalData.get("code");
                    errorMessage = (String)deletedExternalData.get("error");
                    LOGGER.debug("code: " + code + "  message: " + errorMessage);
                }

                requestStatus = this.schedulerService.getSchedulerStatusSuccess();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                requestStatusDesc = "Data(D) Scheduler success";
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                LOGGER.debug("data insert");
            }

            var19 = errorExternalDataList.iterator();

            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                LOGGER.debug(errorCode.get("problemCode") + ": " + errorCode.get("message"));
            }

            return deletedExternalData;
        } catch (Exception var37) {
            requestStatus = this.schedulerService.getSchedulerStatusError();
            this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
            requestStatusDesc = "Data(D) Scheduler error";
            responseDtm = new Timestamp(System.currentTimeMillis());
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
            LOGGER.error("Error fetching data from external API", var37);
            result.put("error", var37.getMessage());
            return result;
        }
    }

    public CoreMap LONAPICCQ00223(CoreMap data) {
        String itemId = "";
        int retryCnt = 0;
        String newUsedFlag = "Y";
        String requestStatus = "";
        String requestId = "";
        String historyId = "";
        Timestamp requestDtm = new Timestamp(System.currentTimeMillis());
        Timestamp responseDtm = new Timestamp(System.currentTimeMillis());
        String requestStatusDesc = "";
        String newErrorMessage = "";
        String newErrorCode = "";
        CoreMap result = new CoreMap();
        CoreMap deletedExternalData = new CoreMap();
        List<CoreMap> errorExternalDataList = new ArrayList();
        int MAX_RETRIES = true;

        try {
            this.insertEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
            this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
            List<CoreMap> usingCodeList = this.interestMapper.searchUsingCodeListWithCycle("Q");
            Iterator var19 = usingCodeList.iterator();

            CoreMap errorCode;
            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                String statCode = (String)errorCode.get("statCode");
                String itemCode1 = (String)errorCode.get("itemCode1");
                String cycle = (String)data.get("cycle");
                String languageType = (String)data.get("languageType");
                String startDate = (String)data.get("startDate");
                String endDate = (String)data.get("endDate");
                String startCount = (String)data.get("startCount");
                String endCount = (String)data.get("endCount");
                itemId = statCode + itemCode1 + cycle;
                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);

                boolean retry;
                String errorMessage;
                do {
                    retry = false;
                    retryCnt = 0;

                    try {
                        String resultMessage;
                        label101: {
                            label100: {
                                requestId = this.comService.getEcosSchedulerNo();
                                historyId = this.comService.getEcosSchedulerDetailNo();
                                requestStatus = this.schedulerService.getSchedulerStatusRequest();
                                this.insertEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                requestDtm = new Timestamp(System.currentTimeMillis());
                                String resultString = (String)this.externalApiController.fetchDataTableList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode1).block();
                                responseDtm = new Timestamp(System.currentTimeMillis());
                                requestStatusDesc = "Master Scheduler request";
                                this.insertEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                                LOGGER.debug("Result from external API: {}", resultString);
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode rootNode = objectMapper.readTree(resultString);
                                errorMessage = rootNode.path("RESULT").path("CODE").asText();
                                resultMessage = rootNode.path("RESULT").path("MESSAGE").asText();
                                switch (errorMessage) {
                                    case "ERROR-100":
                                    case "ERROR-101":
                                    case "ERROR-200":
                                    case "ERROR-300":
                                    case "ERROR-301":
                                    case "ERROR-400":
                                    case "ERROR-500":
                                    case "ERROR-600":
                                    case "ERROR-601":
                                    case "ERROR-602":
                                    case "INFO-100":
                                    case "INFO-200":
                                        requestStatus = this.schedulerService.getSchedulerStatusError();
                                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                                        newErrorCode = errorMessage;
                                        newErrorMessage = resultMessage;
                                        responseDtm = new Timestamp(System.currentTimeMillis());
                                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                                        continue;
                                }

                                result.put("statTableList", resultString);
                                continue;
                            }

                            ++retryCnt;
                            if (retryCnt < 3) {
                                retry = true;
                                LOGGER.debug("Retry attempt {} for statCode: {}, itemCode1: {}, cycle: {}", new Object[]{retryCnt, statCode, itemCode1, cycle});
                            } else {
                                CoreMap retryProblemResult = new CoreMap();
                                retryProblemResult.put("problemCode", itemCode1);
                                retryProblemResult.put("code", errorMessage);
                                retryProblemResult.put("message", "Max retries exceeded. " + resultMessage);
                                errorExternalDataList.add(retryProblemResult);
                            }
                            continue;
                        }

                        requestStatus = this.schedulerService.getSchedulerStatusError();
                        this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                        requestStatusDesc = "Data(Q) Scheduler error";
                        responseDtm = new Timestamp(System.currentTimeMillis());
                        this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, errorMessage, resultMessage, requestStatus);
                        return result;
                    } catch (Exception var36) {
                        if (retryCnt >= 3) {
                            throw var36;
                        }

                        retry = true;
                        LOGGER.debug("Exception on attempt {}: {}", retryCnt, var36.getMessage());
                    }
                } while(retry);

                List<CoreMap> originResult = this.interestMapper.searchOriginalData();
                String newCycle = (String)data.get("cycle");
                deletedExternalData = this.insertNewData(result, originResult, newCycle, data);
                if (deletedExternalData.containsKey("error")) {
                    String code = (String)deletedExternalData.get("code");
                    errorMessage = (String)deletedExternalData.get("error");
                    LOGGER.debug("code: " + code + "  message: " + errorMessage);
                }

                requestStatus = this.schedulerService.getSchedulerStatusSuccess();
                this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
                requestStatusDesc = "Data(Q) Scheduler success";
                responseDtm = new Timestamp(System.currentTimeMillis());
                this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
                LOGGER.debug("data insert");
            }

            var19 = errorExternalDataList.iterator();

            while(var19.hasNext()) {
                errorCode = (CoreMap)var19.next();
                LOGGER.debug(errorCode.get("problemCode") + ": " + errorCode.get("message"));
            }

            return deletedExternalData;
        } catch (Exception var37) {
            requestStatus = this.schedulerService.getSchedulerStatusError();
            this.updateEcosSchedulerManagement(itemId, retryCnt, newUsedFlag, requestStatus, requestId);
            requestStatusDesc = "Data(Q) Scheduler error";
            responseDtm = new Timestamp(System.currentTimeMillis());
            this.updateEcosSchedulerHistory(historyId, requestId, itemId, requestStatusDesc, requestDtm, responseDtm, newErrorCode, newErrorMessage, requestStatus);
            LOGGER.error("Error fetching data from external API", var37);
            result.put("error", var37.getMessage());
            return result;
        }
    }

    public CoreMap insertNewData(CoreMap result, List<CoreMap> originResult, String newCycle, CoreMap data) {
        String newItemCode = new String();
        CoreMap deletedExternalList = new CoreMap();
        CoreMap existedMessage = new CoreMap();
        boolean inserted = false;

        try {
            int isSameData = 0;
            String resultString = (String)result.get("statTableList");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(resultString);
            ArrayNode statisticTableListNode = (ArrayNode)rootNode.path("StatisticSearch").path("row");
            Iterator var15 = statisticTableListNode.iterator();

            String originDataId;
            while(var15.hasNext()) {
                JsonNode node = (JsonNode)var15.next();
                boolean ifSame = false;
                String newStatCode = node.path("STAT_CODE").asText();
                newItemCode = node.path("ITEM_CODE1").asText();
                originDataId = node.path("TIME").asText();
                (new StringBuilder(String.valueOf(newStatCode))).append(newItemCode).append(newCycle).toString();
                String newDataId = newStatCode + newItemCode + newCycle + originDataId;
                ((ObjectNode)node).put("CYCLE", newCycle);
                if (DateFormatUtil.isYear(originDataId)) {
                    ((ObjectNode)node).put("YEAR", DateFormatUtil.getYear(originDataId));
                }

                if (DateFormatUtil.isMonth(originDataId)) {
                    ((ObjectNode)node).put("MONTH", DateFormatUtil.getMonth(originDataId));
                }

                if (DateFormatUtil.isQuarter(originDataId)) {
                    ((ObjectNode)node).put("QUARTER", DateFormatUtil.getQuarter(originDataId));
                } else {
                    LOGGER.debug("Invalid data format: ");
                }

                CoreMap coreMapData = new CoreMap();
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

                while(fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = (Map.Entry)fields.next();
                    coreMapData.put(field.getKey(), ((JsonNode)field.getValue()).asText());
                }

                Iterator var24 = originResult.iterator();

                while(var24.hasNext()) {
                    CoreMap origin = (CoreMap)var24.next();
                    String originDataId = (String)origin.get("dataId");
                    if (newDataId.equals(originDataId)) {
                        ++isSameData;
                        ifSame = true;
                    }
                }

                if (!ifSame) {
                    LOGGER.debug("Inserting new data: " + coreMapData);
                    this.interestMapper.insertNewRealData(coreMapData);
                    inserted = true;
                }
            }

            List<CoreMap> newOriginResult = this.interestMapper.searchMatchedOriginalData(data);
            if (!inserted) {
                Iterator var29 = newOriginResult.iterator();

                while(var29.hasNext()) {
                    CoreMap origin = (CoreMap)var29.next();
                    boolean find = false;
                    originDataId = (String)origin.get("dataId");
                    Iterator var31 = statisticTableListNode.iterator();

                    while(var31.hasNext()) {
                        JsonNode node = (JsonNode)var31.next();
                        String newStatCode = node.path("STAT_CODE").asText();
                        newItemCode = node.path("ITEM_CODE1").asText();
                        String newTime = node.path("TIME").asText();
                        String newDataId = newStatCode + newItemCode + newCycle + newTime;
                        if (originDataId.equals(newDataId)) {
                            find = true;
                            break;
                        }
                    }

                    if (!find) {
                        deletedExternalList.put("DataId", originDataId);
                    }
                }
            }

            if (isSameData == statisticTableListNode.size()) {
                existedMessage.put("code", newItemCode);
                existedMessage.put("error", "이미 데이터가 존재합니다.");
                LOGGER.error("@#@#@#", existedMessage);
                return existedMessage;
            }
        } catch (Exception var26) {
            LOGGER.error("Error parsing or comparing data", var26);
            System.out.println("@@@ " + var26.getMessage());
        }

        return deletedExternalList;
    }

    public List<CoreMap> LONAPITST00222(CoreMap param) throws Exception {
        return this.interestMapper.searchRealData(param);
    }

    public void deleteOriginalMaster() {
        this.interestMapper.deleteOriginalMaster();
    }

    public void deleteOriginalItem() {
        this.interestMapper.deleteOriginalItem();
    }

    public void deleteOriginalData() {
        this.interestMapper.deleteOriginalData();
    }

    public CoreMap insertNewMaster(CoreMap result, List<CoreMap> originResult) {
        CoreMap returnResult = new CoreMap();

        try {
            String resultString = (String)result.get("statTableList");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(resultString);
            ArrayNode statisticTableListNode = (ArrayNode)rootNode.path("StatisticTableList").path("row");
            Iterator var9 = statisticTableListNode.iterator();

            while(var9.hasNext()) {
                JsonNode node = (JsonNode)var9.next();
                String newStatCode = node.path("STAT_CODE").asText();
                boolean found = false;
                Iterator var13 = originResult.iterator();

                while(var13.hasNext()) {
                    CoreMap origin = (CoreMap)var13.next();
                    String originStatCode = (String)origin.get("statCode");
                    if (newStatCode.equals(originStatCode)) {
                        ((ObjectNode)node).put("USED_FLAG", (String)origin.get("usedFlag"));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    ((ObjectNode)node).put("USED_FLAG", "0");
                }
            }

            List<CoreMap> coreMapList = new ArrayList();
            Iterator var18 = statisticTableListNode.iterator();

            while(var18.hasNext()) {
                JsonNode node = (JsonNode)var18.next();
                CoreMap coreMap = new CoreMap();
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

                while(fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = (Map.Entry)fields.next();
                    coreMap.put(field.getKey(), ((JsonNode)field.getValue()).asText());
                }

                coreMapList.add(coreMap);
            }

            returnResult.put("StatisticTableList", coreMapList);
        } catch (Exception var15) {
            LOGGER.error("Error parsing or comparing data", var15);
        }

        System.out.println("###");
        System.out.println(returnResult);
        return returnResult;
    }

    public CoreMap insertNewItem(CoreMap result, List<CoreMap> originResult) {
        CoreMap returnResult = new CoreMap();
        ArrayNode newItemListNode = this.om.createArrayNode();

        try {
            List<String> changeResultToList = (List)result.get("statItemList");

            for(int i = 0; i < changeResultToList.size(); ++i) {
                System.out.println((String)changeResultToList.get(i) + "   ");
                String jsonString = (String)changeResultToList.get(i);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonString);
                ArrayNode statisticItemListNode = (ArrayNode)rootNode.path("StatisticItemList").path("row");

                JsonNode node;
                for(Iterator var12 = statisticItemListNode.iterator(); var12.hasNext(); newItemListNode.add(node)) {
                    node = (JsonNode)var12.next();
                    String newItemCode = node.path("ITEM_CODE").asText();
                    String newCycle = node.path("CYCLE").asText();
                    boolean isSameData = false;
                    Iterator var17 = originResult.iterator();

                    while(var17.hasNext()) {
                        CoreMap origin = (CoreMap)var17.next();
                        String originItemCode = (String)origin.get("itemCode");
                        String originCycle = (String)origin.get("cycle");
                        if (newItemCode.equals(originItemCode) && newCycle.equals(originCycle)) {
                            ((ObjectNode)node).put("USED_FLAG", (String)origin.get("usedFlag"));
                            isSameData = true;
                            break;
                        }
                    }

                    if (!isSameData) {
                        ((ObjectNode)node).put("USED_FLAG", "0");
                    }
                }
            }

            List<CoreMap> coreMapList = new ArrayList();
            Iterator var23 = newItemListNode.iterator();

            while(var23.hasNext()) {
                JsonNode newNode = (JsonNode)var23.next();
                CoreMap coreMap = new CoreMap();
                Iterator<Map.Entry<String, JsonNode>> fields = newNode.fields();

                while(fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = (Map.Entry)fields.next();
                    coreMap.put(field.getKey(), ((JsonNode)field.getValue()).asText());
                }

                coreMapList.add(coreMap);
            }

            returnResult.put("StatisticItemList", coreMapList);
        } catch (Exception var20) {
            LOGGER.error("Error parsing or comparing data", var20);
        }

        return returnResult;
    }

    public List<CoreMap> transformData(String rawData) {
        try {
            return CoreJsonUtil.getListCoreMapFromJson(rawData);
        } catch (Exception var3) {
            LOGGER.error("String을 CoreMap으로 변환하는 과정에서 오류 발생", var3);
            throw new RuntimeException("String을 CoreMap으로 변환하는 과정에서 오류 발생", var3);
        }
    }

    public void storeMasterData(List<CoreMap> data) {
        Iterator var3 = data.iterator();

        while(var3.hasNext()) {
            CoreMap dataEntry = (CoreMap)var3.next();
            this.interestMapper.insertExternalMasterDataApi(dataEntry);
        }

    }

    public void storeItemData(List<CoreMap> data) {
        Iterator var3 = data.iterator();

        while(var3.hasNext()) {
            CoreMap dataEntry = (CoreMap)var3.next();
            this.interestMapper.insertExternalItemDataApi(dataEntry);
        }

    }

    public void storeStatData(List<CoreMap> data) {
        CoreMap newData;
        for(Iterator var3 = data.iterator(); var3.hasNext(); this.interestMapper.insertExternalStatDataApi(newData)) {
            newData = (CoreMap)var3.next();
            String time = (String)newData.get("time");
            newData.put("time", time);
            if (DateFormatUtil.isYear(time)) {
                newData.put("year", DateFormatUtil.getYear(time));
            } else if (DateFormatUtil.isMonth(time)) {
                newData.put("month", DateFormatUtil.getMonth(time));
            } else if (DateFormatUtil.isQuarter(time)) {
                newData.put("getQuarter", DateFormatUtil.getQuarter(time));
            } else if (DateFormatUtil.isFullDate(time)) {
                newData.put("getFullDate", DateFormatUtil.getFullDate(time));
            } else {
                LOGGER.debug("Invalid data format: " + newData);
            }
        }

    }

    public CoreMap diffExternalData(CoreMap param) {
        String languageType = (String)param.get("languageType");
        String startCount = (String)param.get("startCount");
        String endCount = (String)param.get("endCount");
        CoreMap result = new CoreMap();
        LOGGER.debug("Fetching data with parameters: languageType={}, startCount={}, endCount={}, statCode={}", new Object[]{languageType, startCount, endCount});
        String resultString = (String)this.externalApiController.fetchStatTableList(languageType, startCount, endCount).block();
        result.put("statTableList", resultString);
        return result;
    }
}
