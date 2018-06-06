package com.ss.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ss.pojo.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class JsonUtil {

	private interface KEYS{
		public final String DATE = "date";
		public final String PRICE = "price";
		public final String S_PRICE = "s_price";
		public final String C_PRICE = "c_price";
		//public final String URL = "url";
		public final String STOCK = "stock";
		public final String SOURCE_ID = "source_id";
		public final String FLIGHT_NOTE = "flight_note";
	}
	
	public static List<Product> parseJson(String jsonStr){
		List<Product> list = new ArrayList<>();
		try {
			JSONArray jsonArray = JSONArray.parseArray(jsonStr);
			if(jsonArray!=null && jsonArray.size()>0){
				Product product;
				for(int k=0; k< jsonArray.size(); k++){
					JSONObject jo = jsonArray.getJSONObject(k);
					product = new Product();
					try {
						product.settDate(new Date(jo.getLongValue(KEYS.DATE)));
						product.settPrice(jo.getDouble(KEYS.PRICE));
						product.settSprice(jo.getDouble(KEYS.S_PRICE));
						product.settCPrice(jo.getDouble(KEYS.C_PRICE));
						product.setStock(jo.getIntValue(KEYS.STOCK));
						product.setSourceId(jo.getString(KEYS.SOURCE_ID));
						product.setFlightNote(jo.getString(KEYS.FLIGHT_NOTE));
						list.add(product);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						continue;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return list;
	}

}
