package com.skylark.lon.api.interest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skylark.frm.core.common.CoreAbstractServiceImpl;
import com.skylark.frm.core.map.CoreMap;
import com.skylark.frm.core.util.CoreDateUtil;
import com.skylark.frm.core.util.CoreJsonUtil;
import com.skylark.lon.api.exchange.external.controller.ExternalExchangeRateController;
import com.skylark.lon.api.exchange.service.ExchangeRateServiceImpl;
import com.skylark.lon.pmg.psd.biz.LonPmgPsdServiceImpl;
import com.skylark.lon.pmg.psd.biz.LonPmgPsdMapper;
import com.skylark.lon.api.interest.external.controller.ExternalApiController;
import com.skylark.lon.api.interest.mapper.InterestMapper;
import com.skylark.lon.api.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 금리 데이터 관리를 위한 서비스 클래스입니다.
 */
@Service("bsApiInterestService")
public class InterestServiceImpl extends CoreAbstractServiceImpl implements InterestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterestServiceImpl.class);
    @Resource(name = "bmApiInterestMapper")
    private InterestMapper interestMapper;
    
    
	 private final ExternalApiController externalApiController;
	 private ObjectMapper om = new ObjectMapper();
	  /**
	     * 필요한 의존성을 주입받아 새로운 서비스 인스턴스를 생성합니다.
	     *
	     * @param externalApiController 외부 API에서 데이터를 가져오는 컨트롤러
	     */
	@Autowired
	public InterestServiceImpl(ExternalApiController externalApiController) {
	    this.externalApiController = externalApiController;
	}
    // Query Endpoint - 데이터 조회
    @Override
    public List<CoreMap> getInterest (CoreMap data) {
        List<CoreMap> interests = (List<CoreMap>) interestMapper.getInterest(data);
        return interests;
    }


    @Override
    public void updateInterest (String id, CoreMap data) {

    }

    //Master table 조회
    @Override
    public List<CoreMap> LONPMGTST00222 (CoreMap param) throws Exception {
        CoreMap newData = new CoreMap();
        newData.put("test", "test");
//        LOGGER.debug("@@@@@@@");
//        System.out.print(interestMapper.searchMasterFrmAppEac(param));
        return interestMapper.searchMasterFrmAppEac(param); 
        //CoreMap으로 된 Master Table data를 불러옴
    }

    //item table 조회
    @Override
    public List<CoreMap> LONPMGTST00221 (CoreMap param) throws Exception {
        return interestMapper.searchItemFrmAppEac(param);
    }

    //Master table 수정
    @Override
    public void LONPMGTST009 (CoreMap param) throws Exception {
        if (!param.get("datas").equals("")) {
            @SuppressWarnings("unchecked")
            List<CoreMap> datas = (List<CoreMap>) param.get("datas");     

            for (CoreMap data : datas) {
                data.put("userId", getSysUserId());
                if (data.get("statCode") != null && !data.get("statCode").equals("")) {
                    interestMapper.updateMasterFrmAppEac(data);
                }
            }
        }
    }


    @Override
    public void LONPMGTST019 (CoreMap param) throws Exception {
        if (!param.get("datas").equals("")) {
            @SuppressWarnings("unchecked")
            List<CoreMap> datas = (List<CoreMap>) param.get("datas");
            System.out.println("@@@@ " + datas.toString());
            for (CoreMap data : datas) {
                data.put("userId", getSysUserId());
                if (data.get("statCode") != null && !data.get("statCode").equals("")) {
                    // statCode와 itemCode와 cycle을 합친 값을 생성
                    String itemId = data.get("statCode").toString() + data.get("itemCode").toString() + data.get("cycle").toString();

                    // 생성한 itemId를 data에 추가
                    data.put("itemId", itemId);
                    // interestMapper에서 update를 수행할 때 itemId를 조건으로 사용
                    interestMapper.updateItemFrmAppEac(data);
                    System.out.println("****" + datas.toString());
                }
            }
        }
    }
    
 
    /**
     * 외부 Open API에서 데이터를 불러옵니다.
     *
     * @param 한은 API data 정보를 포함하는 CoreMap 리스트
     */
   
    //master data 조회
    //수정 전 코드
//    @Override
//    public CoreMap LONAPIEXT00221 (String authKey, String languageType, String startCount, String endCount, String statCode) {
//    	
//    	CoreMap result = new CoreMap();
//    	result.put("resultApi", externalApiController.fetchStatTableList(languageType, startCount, endCount, statCode)); 
//    			
//		return result;
//    }
    
     // Mono<String>의 결과를 기다려서 실제 문자열을 가져온 후 CoreMap에 해당 문자열을 추가
//    @Override
//    public Mono<CoreMap> LONAPIEXT00221(String authKey, String languageType, String startCount, String endCount, String statCode) {
//        return externalApiController.fetchStatTableList(languageType, startCount, endCount, statCode)
//                .map(resultString -> {
//                    CoreMap result = new CoreMap();
//                    result.put("resultApi", resultString);
//                    return result;
//                });
//    }
    
    @Override
    public CoreMap LONAPIEXT00221(CoreMap data) {
        // 동기적으로 데이터를 가져와서 CoreMap 생성
    	
    	   CoreMap result = new CoreMap();
           try {
        	   LOGGER.debug("data@@@:{}", data);
               String languageType = (String) data.get("languageType");
               String startCount = (String) data.get("startCount");
               String endCount = (String) data.get("endCount");

               LOGGER.debug("Fetching data with parameters: languageType={}, startCount={}, endCount={}, statCode={}", 
                            languageType, startCount, endCount);

               // 동기적으로 데이터를 가져와서 CoreMap 생성
               String resultString = externalApiController.fetchStatTableList(languageType, startCount, endCount).block(); // block() 메서드를 사용하여 Mono의 결과를 동기적으로 가져옴
               LOGGER.debug("Result from external API: {}", resultString);
                result.put("statTableList", resultString);
               
           } catch (Exception e) {
               LOGGER.error("Error fetching data from external API", e);
               result.put("error", e.getMessage());
           }
           
           List<CoreMap> originResult = interestMapper.searchOriginalMaster();
           deleteOriginalMaster();
           
         interestMapper.insertNewMasterData(insertNewMaster(result, originResult));
           LOGGER.debug("@@@@@");
//         deleteMasterStatCode(result);
           LOGGER.debug("멈춤");
           
           return result; //CoreMap으로 된 외부 api와 내부 api를 비교해서 나온 데이터를 반환
    }
    
    @Override
    public CoreMap LONAPIEXT00222(CoreMap data) {
    	
//    	diffExternalData(data);
        CoreMap result = new CoreMap();
//        CoreMap masterUsedFlagData = new CoreMap();
        
        try {
            // UsedFlag와 statCode가 담긴 Master Table Data
            List<CoreMap> masterDataList = interestMapper.searchMasterUsedFlag();
//            masterUsedFlagData.put("masterUsedFlagData", masterDataList);
//            LOGGER.debug("data@@@:{}", masterUsedFlagData);
            
            // statCode값을 저장할 List
            List<String> statCodes = new ArrayList<>();
            
            for (CoreMap record : masterDataList) {
                Object usedFlag = record.get("usedFlag");
                if (usedFlag != null && usedFlag.equals("1")) {
                    String statCode = (String) record.get("statCode");
                    if (statCode != null) {
                        statCodes.add(statCode);
                    }
                }
            }

            
            // Fetch data for each statCode
            List<String> statItemList = new ArrayList<>();
            for (String statCode : statCodes) {
                String languageType = (String) data.get("languageType");
                String startCount = (String) data.get("startCount");
                String endCount = (String) data.get("endCount");

                String resultString = externalApiController.fetchItemTableList(languageType, startCount, endCount, statCode).block(); // block() 메서드를 사용하여 Mono의 결과를 동기적으로 가져옴
                statItemList.add(resultString);
//                result.put("statItemList", resultString);
            }
            
            result.put("statItemList", statItemList);
            
        } catch (Exception e) {
            LOGGER.error("Error fetching data from external API", e);
            result.put("error", e.getMessage());
        }
        List<CoreMap> originResult = interestMapper.searchOriginalItem();
        
        deleteOriginalItem();
        
        
        interestMapper.insertNewItemListData(insertNewItem(result, originResult));
        
        LOGGER.debug("멈춤");
        return result;
    }

    @Override
    public CoreMap LONAPIEXT00223(CoreMap data) {
    	 CoreMap result = new CoreMap();
         try {
      	   LOGGER.debug("data@@@:{}", data);
      	   	 String statCode = (String) data.get("statCode");
      	   	 String itemCode1 = (String) data.get("itemCode1");
      	   	 String cycle = (String) data.get("cycle");
      	   	 String startDate = (String) data.get("startDate");
      	   	 String endDate = (String) data.get("endDate");
             String languageType = (String) data.get("languageType");
             String startCount = (String) data.get("startCount");
             String endCount = (String) data.get("endCount");
             String itemCode2 = (String) data.get("itemCode2");
             String itemCode3 = (String) data.get("itemCode3");
             String itemCode4 = (String) data.get("itemCode4");
             
//             LOGGER.debug("Fetching data with parameters: languageType={}, startCount={}, endCount={}, statCode={}", 
//                          languageType, startCount, endCount);

             // 동기적으로 데이터를 가져와서 CoreMap 생성
             String resultString = externalApiController.fetchDataTableList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode1, itemCode2, itemCode3, itemCode4).block(); // block() 메서드를 사용하여 Mono의 결과를 동기적으로 가져옴
             LOGGER.debug("Result from external API: {}", resultString);
              result.put("statTableList", resultString);
         
         } catch (Exception e) {
             LOGGER.error("Error fetching data from external API", e);
             result.put("error", e.getMessage());
         }
         
         // TODO : Y 인것들만 화면에서 파라미터를 넘겨주기때문에 Y 인 데이터만 따로 조회할 필요없음. 검증필요없음.
         
         List<CoreMap> originResult = interestMapper.searchOriginalData();
         //deleteOriginalData();
         String newCycle = (String) data.get("cycle");
         
//         interestMapper.insertNewRealData(insertNewData(result, originResult, newCycle));
         CoreMap deletedExternalData = insertNewData(result, originResult, newCycle);
         LOGGER.debug("멈춤");
         
         return deletedExternalData; //CoreMap으로 된 외부 api와 내부 api를 비교해서 나온 데이터를 반환
    
    }


    @Override
    public CoreMap insertNewData(CoreMap result, List<CoreMap> originResult, String newCycle) {
    	 
    	 CoreMap deletedExternalList = new CoreMap();
    	 boolean inserted = false;
    	 try {
    		 int isSameData = 0;
    		 String resultString = (String) result.get("statTableList");
             ObjectMapper objectMapper = new ObjectMapper();
             JsonNode rootNode = objectMapper.readTree(resultString); // resultString을 JsonNode로 변환
             ArrayNode statisticTableListNode = (ArrayNode) rootNode.path("StatisticSearch").path("row");
             
             String saveNewItemId = new String();
//             String removeInsertedNewDataId = new String();
//             CoreMap removeInsertedNewDataIdList = new CoreMap();
//             ObjectNode newNode = objectMapper.createObjectNode();
//             newNode.put("STAT_CODE", "0000tst");
//             newNode.put("ITEM_CODE1", "0001tst");
//             newNode.put("TIME", "2021Q1");
//             newNode.put("CYCLE", "A");
//             statisticTableListNode.add(newNode);
             
//             for (int i = 0; i < statisticTableListNode.size(); i++) {
//                 JsonNode node = statisticTableListNode.get(i);
//                 String statCode = node.path("STAT_CODE").asText();
//                 String itemCode = node.path("ITEM_CODE1").asText();
//                 String time = node.path("TIME").asText();
//
//                 if (statCode.equals("722Y001") && itemCode.equals("0101000") && time.equals("2021")) {
//                     statisticTableListNode.remove(i);
//                     break;
//                 }
//             }
             for (JsonNode node : statisticTableListNode) {
            	 boolean ifSame = false;    // TODO : 변수 할당부분 헷갈린점. 
                 String newStatCode = node.path("STAT_CODE").asText();
                 String newItemCode = node.path("ITEM_CODE1").asText();
                 String newTime = node.path("TIME").asText();
                 String newItemId = newStatCode + newItemCode + newCycle;
                 saveNewItemId = newItemId;
                 String newDataId = newStatCode + newItemCode + newCycle + newTime; // TODO : 파라미터 조립부분 확인.
                 ((ObjectNode) node).put("CYCLE",newCycle);
                 
                 System.out.println("newTime: " + newTime);  // 디버깅용 출력
                 System.out.println("isYear: " + DateFormatUtil.isYear(newTime));  // 디버깅용 출력
                 System.out.println("isMonth: " + DateFormatUtil.isMonth(newTime));  // 디버깅용 출력
                 System.out.println("isQuarter: " + DateFormatUtil.isQuarter(newTime));  // 디버깅용 출력
            	 
                 if (DateFormatUtil.isYear(newTime)) {
            		  ((ObjectNode) node).put("YEAR",DateFormatUtil.getYear(newTime));
                 }
                 if (DateFormatUtil.isMonth(newTime)) {
                	 ((ObjectNode) node).put("MONTH",DateFormatUtil.getMonth(newTime));
//                         String month = DateFormatUtil.getMonth(newTime);
//                         System.out.println("Extracted Month: " + month);  // 디버깅 메시지
//                         ((ObjectNode) node).put("MONTH", month);
//                         System.out.println("Node after put: " + node.toString());  // 디버깅 메시지
                     
                 }
                 if (DateFormatUtil.isQuarter(newTime)) {
                	 ((ObjectNode) node).put("QUARTER",DateFormatUtil.getQuarter(newTime));
                     
                 } else {
                     // 형식에 맞지 않는 데이터의 처리 방법 정의 (예: 무시하거나 예외 발생)
                     // 이 경우엔 일단 무시
                     LOGGER.debug("Invalid data format: ");
                }
          	
                 CoreMap coreMapData = new CoreMap();
                 Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                 while (fields.hasNext()) {
                     Map.Entry<String, JsonNode> field = fields.next();

                     coreMapData.put(field.getKey(), field.getValue().asText());
                 }

                 //외부와 내부 데이터 모두 같은지 판별
                 for (CoreMap origin : originResult) {
                     String originDataId = (String) origin.get("dataId");
                     
                     if (newDataId.equals(originDataId)) { //외부 = 내부이면
                    	 
                    	  isSameData++;
                    	  ifSame = true;
                     }
                 }
 
                 if (ifSame == false) { //new data중 하나가 origin 전체에 없다는 것
                	 //coreMapData에서 dataId를 빼내서 removeInsertedNewDataId에 저장
                	 
//                	 String insertedStatCode = (String) coreMapData.get("statCode");
//                	 String insertedItemCode = (String) coreMapData.get("itemCode1");
//                	 String insertedCycle = (String) coreMapData.get("cycle");
//                	 String insertedDate = (String) coreMapData.get("time");
//                	 removeInsertedNewDataId = insertedStatCode + insertedItemCode + insertedCycle + insertedDate;
//                	 removeInsertedNewDataIdList.put("removeInsertedNewDataId", removeInsertedNewDataIdList);
//                	 String wgtStr = (String) coreMapData.get("wgt");
//                	 if (wgtStr.equals("") || wgtStr.equals("null")) {
//                		 
//                	 }
                	 LOGGER.debug("Inserting new data: " + coreMapData);
                	 interestMapper.insertNewRealData(coreMapData); //없는 데이터 하나 insert
                	 inserted = true; 
                	 System.out.println("@@@@@@@@@@@@@@");
//                	  LOGGER.debug("Data inserted successfully: " + coreMapData);
                 }
             }
             
             List<CoreMap> newOriginResult = interestMapper.searchMatchedOriginalData(saveNewItemId);
             
             //removeInsertedNewDataIdList를 newDataId에서 삭제처리
//           아 이건 못해 진짜
//             for ()
             
             //내부의 데이터가 외부에서 삭제되었는지 판별
             if (!inserted) {
             for (CoreMap origin : newOriginResult) {
            	 boolean find = false;
                 String originDataId = (String) origin.get("dataId");
                 
                 for (JsonNode node : statisticTableListNode) {
                     String newStatCode = node.path("STAT_CODE").asText();
                     String newItemCode = node.path("ITEM_CODE1").asText();
                     String newTime = node.path("TIME").asText();
                     String newDataId = newStatCode + newItemCode + newCycle + newTime; // newCycle은 요청변수에만 있고 API 결과에는 포함 안 되어 있음
                     
                     if (originDataId.equals(newDataId)) {
                    	find = true;
                    	break;
                     }
                 }
                 
                 if (find == false) {
                	 deletedExternalList.put("DataId", originDataId);
                	 //origin data를 삭제
                	 
                 }
               }
             }
             //가져온 데이터가 원본데이터에 모두 있을 때
             if (isSameData == statisticTableListNode.size()) {
            	  throw new Exception("이미 데이터가 존재합니다.");
             }

         } catch (Exception e) {
             LOGGER.error("Error parsing or comparing data", e);
             System.out.println("@@@ " + e.getMessage());
         }

    	
         return deletedExternalList;
    	 
    }
    
    //[306010] Data Api Search
    @Override
    public List<CoreMap> LONAPITST00222 (CoreMap param) throws Exception {
    	 return interestMapper.searchRealData(param);
    
    } 
    //기존 Master Data 삭제
    @Override
    public void deleteOriginalMaster() {
    	interestMapper.deleteOriginalMaster();
    }
    
    @Override
    public void deleteOriginalItem() {
    	interestMapper.deleteOriginalItem();
    }
    
    @Override
    public void deleteOriginalData() {
    	interestMapper.deleteOriginalData();
    }
//    기존 data의 statCode와 새로운 data의 statCode가 같으면 같은 data의 usedFlag를 기존 data의 것으로 insert
    @Override
    public CoreMap insertNewMaster(CoreMap result, List<CoreMap> originResult) {
        CoreMap returnResult = new CoreMap();
//        CoreMap foundDeletedStatCode = new CoreMap();
        try {
            String resultString = (String) result.get("statTableList");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(resultString); // resultString을 JsonNode로 변환
            ArrayNode statisticTableListNode = (ArrayNode) rootNode.path("StatisticTableList").path("row");

            for (JsonNode node : statisticTableListNode) {
                String newStatCode = node.path("STAT_CODE").asText();
                boolean found = false;
//                boolean foundStatCode = false;
                
                for (CoreMap origin : originResult) {
                    String originStatCode = (String) origin.get("statCode");
                    if (newStatCode.equals(originStatCode)) {
                        // originStatCode와 newStatCode가 같으면 필요한 로직 처리
                        ((ObjectNode) node).put("USED_FLAG", (String) origin.get("usedFlag"));
                        found = true;
                        break;
                    }
                    //newStatCode에 originStatCode가 있으면 foundStatCode = true
//                    if (originStatCode.equals(newStatCode)) {
//                    	foundStatCode = true;
//                    }else {
//                    	foundDeletedStatCode.put("deletedStatCode", originStatCode);
//                    }
                }
                
                if (!found) {
                    ((ObjectNode) node).put("USED_FLAG", "0");
                }
            }

            // JSON 데이터를 CoreMap 형태로 변환
            List<CoreMap> coreMapList = new ArrayList<>();
            for (JsonNode node : statisticTableListNode) {
                CoreMap coreMap = new CoreMap();
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    coreMap.put(field.getKey(), field.getValue().asText());
                }
                coreMapList.add(coreMap);
            }

            // returnResult에 추가
            returnResult.put("StatisticTableList", coreMapList);

        } catch (Exception e) {
            LOGGER.error("Error parsing or comparing data", e);
        }

        System.out.println("###");
        System.out.println(returnResult);
        return returnResult;
    }


    @Override
    public CoreMap insertNewItem(CoreMap result, List<CoreMap> originResult) {
        CoreMap returnResult = new CoreMap();
//        CoreMap deletedItemCodeCycleData = new CoreMap();
        ArrayNode newItemListNode = om.createArrayNode();

        try {
          List<String> changeResultToList = (List<String>) result.get("statItemList");
          System.out.println("@@@" + changeResultToList.get(1));
          
         for (int i = 0; i < changeResultToList.size(); i++) {
        	 System.out.println(changeResultToList.get(i) + "   ");
        	 
        	 String jsonString =  changeResultToList.get(i);

        	ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString); // resultString을 JsonNode로 변환
            ArrayNode statisticItemListNode = (ArrayNode) rootNode.path("StatisticItemList").path("row");
            //CoreMap diffMap = new CoreMap();
            
//            Set <String> uniqueOriginitems = new HashSet<String>();            
//            Set <String> newUniqueitems = new HashSet<String>();            

            for (JsonNode node : statisticItemListNode) {
                String newStatCode = node.path("STAT_CODE").asText();
                String newItemCode = node.path("ITEM_CODE").asText();
                String newCycle = node.path("CYCLE").asText();
//                String newUniqueitem = newStatCode + newItemCode + newCycle;
                boolean isSameData = false;
//                boolean isOriginItemidExist = false;
                
                for (CoreMap origin : originResult) {
                	String originStatCode = (String) origin.get("statCode");
                    String originItemCode = (String) origin.get("itemCode");
                    String originCycle = (String) origin.get("cycle");
//                    String uniqueOriginitem = originStatCode + originItemCode + originCycle; 
//                    uniqueOriginitems.add(uniqueOriginitem);
                    
                    // 	내부 외부 둘다있음.
                    if (newItemCode.equals(originItemCode) && newCycle.equals(originCycle)) {                    	
                        // originItemCode와 newItemCode가 같으면 필요한 로직 처리
                        ((ObjectNode) node).put("USED_FLAG", (String) origin.get("usedFlag"));
                        isSameData = true;
                        break;
                    }
                  
                }
//                	newUniqueitems.add(newUniqueitem);
                
                if (!isSameData) {
                    ((ObjectNode) node).put("USED_FLAG", "0");
                }
                //새로운 node를 만들어 usedFlag가 추가된 statisticItemListNode를 담음
		        newItemListNode.add(node);
            }
         }
            // JSON 데이터를 CoreMap 형태로 변환
	        List<CoreMap> coreMapList = new ArrayList<>();
	        for (JsonNode newNode : newItemListNode) {
	            CoreMap coreMap = new CoreMap();
	            Iterator<Map.Entry<String, JsonNode>> fields = newNode.fields();
	            while (fields.hasNext()) {
	                Map.Entry<String, JsonNode> field = fields.next();
	                coreMap.put(field.getKey(), field.getValue().asText());
	            }
	            coreMapList.add(coreMap);
	        }
            // returnResult에 추가
            returnResult.put("StatisticItemList", coreMapList);

//        } 
         } catch (Exception e) {
            LOGGER.error("Error parsing or comparing data", e);
        }
        	//System.out.println("@@@### " + returnResult);
        return returnResult;
    }

    
    
    // CoreMap을 JSON 문자열로 변환하는 메서드
    private String convertCoreMapToJsonString(CoreMap coreMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(coreMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //item data 조회
//    @Override
//    public Mono<String> LONAPIEXT00222 (String authKey, String languageType, String startCount, String endCount, String statCode) {
//    	
//		return externalApiController.fetchStatItemList(languageType, startCount, endCount, statCode);
//    }
//    
    //실data조회
//   	@Override
//   	public Mono<String> LONAPIEXT00223(String authKey, String languageType, String startCount, String endCount,
//   			String statCode, String cycle, String startDate, String endDate, String itemCode, String itemCode2,
//   			String itemCode3, String itemCode4) {
//   		return externalApiController.fetchStatDataList(languageType, startCount, endCount, statCode, cycle, startDate, endDate, itemCode, itemCode2, itemCode3, itemCode4);
//   	}
//   	
    //LONAPITST00221 (내부 Master Table Data)와 fetchStatTableList(외부 Master Data)를 비교해서 다른 값 return
//    public List<CoreMap> comparisonMaster(List<CoreMap> intMasterData, List<CoreMap> extMasterData){
    	
    	
//    }
    
    //LONAPITST00223(내부 Item Table Data)와 fetchStatItemList(외부 Item Data)를 비교해서 다른 값 return
// public List<CoreMap> comparisonItem(List<CoreMap> intItemData, List<CoreMap> extItemData){
//    	
//    	
//    }
    /**
     * 불러온 문자열(xml/json)을 CoreMap 리스트로 변환합니다.
     *
     * @param rawRata xml 형태의 원시 문자열 데이터
     * @return List<CoreMap> 변환된 데이터 리스트
     */
    public List<CoreMap> transformData (String rawData) {
        try {
            return CoreJsonUtil.getListCoreMapFromJson(rawData);
        } catch (Exception e) {
            LOGGER.error("String을 CoreMap으로 변환하는 과정에서 오류 발생", e);
            throw new RuntimeException("String을 CoreMap으로 변환하는 과정에서 오류 발생", e);
        }
    }
    
    /**
     * 데이터베이스에 한은 API 데이터를 저장합니다.
     *
     * @param 한은 API data 정보를 포함하는 CoreMap 리스트
     */
    
    
    public void storeMasterData (List<CoreMap> data) { //CoreMap으로 받아온 data를 저장하는 메서드
        for (CoreMap dataEntry : data) {
            interestMapper.insertExternalMasterDataApi(dataEntry);
        }
    }
    
    public void storeItemData (List<CoreMap> data) { //CoreMap으로 받아온 data를 저장하는 메서드
    	 for (CoreMap dataEntry : data) {
             interestMapper.insertExternalItemDataApi(dataEntry);
      }
    }
    public void storeStatData (List<CoreMap> data) { //CoreMap으로 받아온 data를 저장하는 메서드
    	//가져온 data중 time을 형식에 맞춰서 가공
    		for (CoreMap newData : data) {        
    			String time = (String)newData.get("time");
    			newData.put("time",time);
        	
        	 if (DateFormatUtil.isYear(time)) {
        		 newData.put("year",DateFormatUtil.getYear(time));
             } else if (DateFormatUtil.isMonth(time)) {
            	 newData.put("month",DateFormatUtil.getMonth(time));
             
             } else if (DateFormatUtil.isQuarter(time)) {
            	 newData.put("getQuarter",DateFormatUtil.getQuarter(time));
                 
             } else if (DateFormatUtil.isFullDate(time)) {
            	 newData.put("getFullDate",DateFormatUtil.getFullDate(time));
           } else {
                 // 형식에 맞지 않는 데이터의 처리 방법 정의 (예: 무시하거나 예외 발생)
                 // 이 경우엔 일단 무시
                 LOGGER.debug("Invalid data format: " + newData);
            }
      	
       	
            interestMapper.insertExternalStatDataApi(newData);
      }
    }
    
    
    public CoreMap diffExternalData (CoreMap param) {

    	// 내부 master 데이터 , 내부 items 데이터
    	List<CoreMap> myInterestMasterDatas = interestMapper.searchOriginalMaster();
    	List<CoreMap> myInterestItemDatas = interestMapper.searchOriginalItem();

    	String languageType = (String) param.get("languageType");
    	String startCount = (String) param.get("startCount");
    	String endCount = (String) param.get("endCount");

       CoreMap result = new CoreMap();

       LOGGER.debug("Fetching data with parameters: languageType={}, startCount={}, endCount={}, statCode={}", 
                    languageType, startCount, endCount);

       // 외부 master 데이터
       String resultString = externalApiController.fetchStatTableList(languageType, startCount, endCount).block(); // block() 메서드를 사용하여 Mono의 결과를 동기적으로 가져옴
        
       //HashSet <CoreMap> = new HashSet<>();
    	
       result.put("statTableList", resultString);
    	
    	return result;
    }
}
