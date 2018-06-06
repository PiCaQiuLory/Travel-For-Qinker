package com.ss.api;

import com.ss.pojo.ctrip.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class OPENAPI {
	

	public GetTodoOrderListResponse GetTodoOrderList(String root, GetTodoOrderListRequest request) throws Exception {
		GetTodoOrderListResponse result = new GetTodoOrderListResponse();

		String method = "GetTodoOrderList";

		String requestString = SDKCore.<GetTodoOrderListRequest> ObjToXMLString(request);

		String responseString = SDKCore.PostRequest(root, requestString, method);

		result = SDKCore.<GetTodoOrderListResponse> XMLStringToObj(GetTodoOrderListResponse.class, responseString);

		return result;
	}

	public UpdateProductPriceResponse UpdateProductPrice(String root, UpdateProductPriceRequest request) throws Exception {
		UpdateProductPriceResponse result = new UpdateProductPriceResponse();
		String requestString = SDKCore.<UpdateProductPriceRequest> ObjToXMLString(request);
		// some special replacement for ctrip api  携程的api写的真垃圾
		if(requestString.contains("\n               <SinglePersonCostPrice>0.0</SinglePersonCostPrice>")){
			requestString = requestString.replaceAll("\n               <SinglePersonCostPrice>0.0</SinglePersonCostPrice>", "");
		}
        if(requestString.contains("\n               <SinglePersonSalePrice>0.0</SinglePersonSalePrice>")){
        	requestString = requestString.replaceAll("\n               <SinglePersonSalePrice>0.0</SinglePersonSalePrice>", "");
		}
        if(requestString.contains(" class=\"java.util.ArrayList\"")){
        	requestString = requestString.replaceAll(" class=\"java.util.ArrayList\"", "");
		}
        if(requestString.contains("<optionPriceInfo>")){
        	requestString = requestString.replaceAll("<optionPriceInfo>", "<OptionPriceInfo>");
		}
        if(requestString.contains("</optionPriceInfo>")){
        	requestString = requestString.replaceAll("</optionPriceInfo>", "</OptionPriceInfo>");
		}
		log.debug("UpdateProductPrice request xml is :" +requestString);
		
		String responseString = SDKCore.PostRequest(root,requestString, "UpdateProductPrice");
		log.debug("UpdateProductPrice response xml is :" +responseString);
		if(!responseString.equals("")){
			result = SDKCore.<UpdateProductPriceResponse> XMLStringToObj(UpdateProductPriceResponse.class, responseString);
		}
		if(requestString.contains("11036113")){
			log.warn("UpdateProductPrice request xml is :" +requestString);
			log.warn("UpdateProductPrice response xml is :" +responseString);
		}
		return result;
	}
	
	public UpdateProductInventoryResponse UpdateProductInventory(String root, UpdateProductInventoryRequest request) throws Exception { 
		UpdateProductInventoryResponse result = new UpdateProductInventoryResponse();
		String requestString = SDKCore.<UpdateProductInventoryRequest>ObjToXMLString(request);
        // some special replacement for ctrip api 
		if(requestString.contains(" class=\"java.util.ArrayList\"")){
        	requestString = requestString.replaceAll(" class=\"java.util.ArrayList\"", "");
        }
		if(requestString.contains("\n               <SharedInventoryQuantity>0</SharedInventoryQuantity>")){
			requestString = requestString.replaceAll("\n               <SharedInventoryQuantity>0</SharedInventoryQuantity>", "");
		}
		if(requestString.contains("\n               <ReservedInventoryCleanUpHour>0</ReservedInventoryCleanUpHour>")){
			requestString = requestString.replaceAll("\n               <ReservedInventoryCleanUpHour>0</ReservedInventoryCleanUpHour>", "");
		}
		if(requestString.contains("\n               <ReservedInventoryCleanUpDays>0</ReservedInventoryCleanUpDays>")){
			requestString = requestString.replaceAll("\n               <ReservedInventoryCleanUpDays>0</ReservedInventoryCleanUpDays>", "");
		}
		log.debug("UpdateProductInventoryResponse request xml is :" +requestString);
		String responseString = SDKCore.PostRequest(root,requestString, "UpdateProductInventory");
		log.debug("UpdateProductInventoryResponse response xml is :" +responseString);
		if(!responseString.equals("")){
			result = SDKCore.<UpdateProductInventoryResponse>XMLStringToObj(UpdateProductInventoryResponse.class, responseString);
		}	
		if(requestString.contains("11036113")){
			log.warn("UpdateProductInventoryResponse request xml is :" +requestString);
			log.warn("UpdateProductInventoryResponse response xml is :" +responseString);
		}
		return result;
	}

}
