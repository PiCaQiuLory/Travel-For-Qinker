package com.ss.api;

import com.ss.pojo.OutputTemlate;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PontusTravelItemSkuInfo;
import com.taobao.api.domain.PontusTravelPrices;
import com.taobao.api.request.AlitripTravelItemSkuOverrideRequest;
import com.taobao.api.response.AlitripTravelItemSkuOverrideResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AliService {
	private static final String serverUrl ="http://gw.api.taobao.com/router/rest";
	private static final String appKey = "23481215";
	private static final String appSecret = "1e5061c660f996ecd925e3f8c22d12d1";

	public static int updatePriceAndStock(List<OutputTemlate> otList){
		if(otList==null || otList.size()==0){
			return 0;
		}
		
		//group by productId
		Map<String, List<OutputTemlate>> proMap = new HashMap<>();
		for (OutputTemlate outputTemlate : otList) {
			if(outputTemlate.getStock()<1){
				continue;
			}
			if(proMap.containsKey(outputTemlate.getTargetProductId())){
				proMap.get(outputTemlate.getTargetProductId()).add(outputTemlate);
			}else{
				List<OutputTemlate> list = new ArrayList<>();
				list.add(outputTemlate);
				proMap.put(outputTemlate.getTargetProductId(), list);
			}
		}
		int count = proMap.size();
		for(List<OutputTemlate> list : proMap.values()) {
			boolean result = sent(list);
			if(result){
				// isSentTrue
				for (OutputTemlate outputTemlate : list) {
					outputTemlate.setSent(true);
				}
			}
		}
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean sent(List<OutputTemlate> otList){
		
		TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
		AlitripTravelItemSkuOverrideRequest req = new AlitripTravelItemSkuOverrideRequest();
		
		OutputTemlate otInstance = otList.get(0);
		
		// id in aliCtip
		req.setItemId(Long.valueOf(otInstance.getTargetProductId()));
		List skus = new ArrayList<PontusTravelItemSkuInfo>();
		//group by package name
		Map<String, List<OutputTemlate>> packageMap = new HashMap<>();
		for (OutputTemlate ot : otList) {
			if(packageMap.containsKey(ot.getPackageName())){
				packageMap.get(ot.getPackageName()).add(ot);
			}else{
				List<OutputTemlate> list = new ArrayList<>();
				list.add(ot);
				packageMap.put(ot.getPackageName(), list);
			}
		}
		
		for(String packageName : packageMap.keySet()){
			PontusTravelItemSkuInfo sku = new PontusTravelItemSkuInfo();
			sku.setPackageName(packageName);
			sku.setOuterSkuId(otInstance.getSourceId()); //source_product_id
			
			List<PontusTravelPrices> priceList = new ArrayList<PontusTravelPrices>();
			
			for (OutputTemlate ot : packageMap.get(packageName)) {
				PontusTravelPrices aPrice = new PontusTravelPrices();
				aPrice.setPriceType(1L);
				aPrice.setStock(Long.valueOf(ot.getStock()));
				aPrice.setPrice(ot.gettPrice().longValue()*100);
				aPrice.setDate(ot.gettDate());
				priceList.add(aPrice);
				PontusTravelPrices sPrice = new PontusTravelPrices();
				sPrice.setPriceType(3L);
				sPrice.setStock(Long.valueOf(ot.getStock()));
				sPrice.setPrice(ot.gettSprice().longValue()*100);
				sPrice.setDate(ot.gettDate());
				priceList.add(sPrice);
				PontusTravelPrices cPrice = new PontusTravelPrices();
				cPrice.setPriceType(2L);
				cPrice.setStock(Long.valueOf(ot.getStock()));
				cPrice.setPrice(ot.gettCPrice().longValue()*100);
				cPrice.setDate(ot.gettDate());
				priceList.add(cPrice);
			}
			
			sku.setPrices(priceList);
			skus.add(sku);
		}
		
		req.setSkus(skus);
		try {
			AlitripTravelItemSkuOverrideResponse response = client.execute(req,otInstance.getToken());
			log.info(response.getMsg());
			log.info(response.getSubMsg());
			
			if(response.getMsg()!=null){
				log.warn("sync failed to ali, target product id is " + otInstance.getTargetProductId() + ", msg is " + response.getSubMsg());
				return false;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return true;
				
	}
	
	public static void main(String[] args) {
		OutputTemlate ot = new OutputTemlate();
		ot.setToken("6201217f9ec27a1a5743d8a3ace32e40cddaaf5f924b9e02995772539");
		ot.setTargetProductId("540684598637");
		ot.setSourceId("539970779726");
		ot.setStock(1);
		ot.settPrice(new Double(500000));
		ot.settCPrice(new Double(500000));
		ot.settSprice(new Double(500000));
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 15);
		ot.settDate(ca.getTime());
		
		OutputTemlate ot1 = new OutputTemlate();
		ot1.setToken("6201217f9ec27a1a5743d8a3ace32e40cddaaf5f924b9e02995772539");
		ot1.setTargetProductId("539970779726");
		ot1.setSourceId("539970779726");
		ot1.setStock(1);
		ot1.settPrice(new Double(510000));
		ot1.settCPrice(new Double(510000));
		ot1.settSprice(new Double(510000));
		Calendar ca1 = Calendar.getInstance();
		ca1.set(Calendar.DAY_OF_MONTH, 16);
		ot1.settDate(ca1.getTime());
		List<OutputTemlate> list = new ArrayList<>();
		ot.setPackageName("ceshi");
		ot1.setPackageName("ceshi");
		list.add(ot);
		list.add(ot1);
		updatePriceAndStock(list);
	}
}
