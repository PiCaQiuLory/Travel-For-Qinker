package com.ss.api;



import com.ss.pojo.OutputTemlate;
import com.ss.pojo.ctrip.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class CtripService {


//	private interface Vendor{
//		public static Long VENDOR_ID = 8817l;
//		public static String VENDOR_TOKEN = "8817";
//	}
	
	public static String syncProduct(OutputTemlate ot) throws Exception{
		// update price
		Price price = new Price();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dtList = new ArrayList<>();
		if(ot.gettDateList()==null || ot.gettDateList().size()==0){
			dtList.add(sdf.format(ot.gettDate()));
			
		}else{
			for (Date dt : ot.gettDateList()) {
				dtList.add(sdf.format(dt));
			}
		}
		
		price.setDateTime(dtList);
		PackagePriceInfo info = new PackagePriceInfo();
		if(ot.gettPrice()<0 || ot.gettSprice()<0 || ot.gettCPrice()<0){
			log.info("price is invalid:" + ot.gettPrice() + "," +ot.gettCPrice()+","+ot.gettSprice());
			return "";
		}
		info.setAdultCostPrice(ot.gettPrice());
		if(new Double(0).compareTo(ot.getCoefficient())==0){
			info.setAdultSalePrice(ot.gettPrice());
		}else{
			BigDecimal coefficient = new BigDecimal(ot.getCoefficient());
			BigDecimal adtSalePrice =new BigDecimal(info.getAdultCostPrice()).divide(new BigDecimal(1).subtract(coefficient),BigDecimal.ROUND_HALF_UP);
			info.setAdultSalePrice(adtSalePrice.setScale(0,RoundingMode.UP).doubleValue());
		}
		info.setChildCostPrice(ot.gettCPrice());
		info.setChildSalePrice(ot.gettCPrice());
		if(ot.gettSprice()!=null && ot.gettSprice().compareTo(0.0)!=0){
			info.setSinglePersonCostPrice(ot.gettSprice());
			info.setSinglePersonSalePrice(ot.gettSprice());
		}
		
		
		List<PackagePriceInfo> infoList = new ArrayList<PackagePriceInfo>();
		infoList.add(info);
		price.setPackagePriceInfoList(infoList);

		if(ot.getPackageList()!=null && ot.getPackageList().size()>0){
			
			List<OptionPriceInfo> optionList = new ArrayList<>();
			for (OutputTemlate pkgInfo : ot.getPackageList()) {
				if(pkgInfo.gettPrice()>0){
					OptionPriceInfo opt = new OptionPriceInfo();
					opt.setVendorOptionCode(pkgInfo.getPackageName());
					opt.setCostPrice(pkgInfo.gettPrice());
					opt.setSalePrice(pkgInfo.gettPrice());
					optionList.add(opt);
				}
				
			}
			price.setOptionPriceInfoList(optionList);
		}
		
		List<Price> list = new ArrayList<Price>();
		list.add(price);
		
		String s = updateProductPrice(ot.getVendorId(),ot.getToken(),ot.getApiUrl(),ot.getTargetProductId(), list);
		
		ot.setSent(true);
		return s;
	}
	
	public static String syncStock(OutputTemlate ot) throws Exception{
		// update stock
		List<String> dtList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(ot.gettDateList()==null || ot.gettDateList().size()==0){
			dtList.add(sdf.format(ot.gettDate()));
		}else{
			for (Date dt : ot.gettDateList()) {
				dtList.add(sdf.format(dt));
			}
		}
		Inventory inventory = new Inventory();
		inventory.setDateList(dtList);
		List<PackageInventoryInfo> packageinventoryinfolist = new ArrayList<>();
		PackageInventoryInfo inventoryInfo = new PackageInventoryInfo();
		inventoryInfo.setInventoryType("Inventory");
		inventoryInfo.setReservedInventoryQuantity(ot.getStock());
		packageinventoryinfolist.add(inventoryInfo);
		inventory.setPackageInventoryInfoList(packageinventoryinfolist);
		List<Inventory> ilist = new ArrayList<>();
		ilist.add(inventory);
		
		return CtripService.updateProductInventory(ot.getVendorId(),ot.getToken(),ot.getApiUrl(),ot.getTargetProductId(), ilist);
	}
	
	public static String updateProductPrice(String vendorId, String token, String url,String vendorProductCode, List<Price> priceList) throws Exception
	{
		
		UpdateProductPriceRequest request = new UpdateProductPriceRequest();
		
		request.SetVendorId(Long.valueOf(vendorId));
		request.SetVendorToken(token);

		//set vendorProductCode
		request.setVendorProductCode(vendorProductCode);
		
		request.setPriceList(priceList);
		
		// Post
		OPENAPI openAPI = new OPENAPI();
		UpdateProductPriceResponse response = openAPI.UpdateProductPrice(url,request);
		
		if(response.getErrorCode() == null){
			log.info("updateProductPrice successfully");
		}
		else{
			log.warn("updateProductPrice failed : " + response.getErrorCode() + ", vendor id is "+ vendorId+", vendorProductCode is " +vendorProductCode);
			log.warn("updateProductPrice failed : " + response.getErrorMsg());
			return vendorProductCode;	
		}
		return "";
	}
	
	public static String updateProductInventory(String vendorId, String token, String url,String vendorProductCode, List<Inventory> inventoryList) throws Exception
	{
		
		UpdateProductInventoryRequest request = new UpdateProductInventoryRequest();
		
		request.SetVendorId(Long.valueOf(vendorId));
		request.SetVendorToken(token);

		//set vendorProductCode
		request.setVendorProductCode(vendorProductCode);
		
		request.setInventoryList(inventoryList);
		
		// Post
		OPENAPI openAPI = new OPENAPI();
		UpdateProductInventoryResponse response = openAPI.UpdateProductInventory(url,request);
		
		if(response.getErrorCode() == null){
			log.info("updateProductInventory successfully");
		}
		else{
			log.warn("updateProductInventory failed : " + response.getErrorCode() + ", vendor id is "+ vendorId+", vendorProductCode is " +vendorProductCode);
			log.warn("updateProductInventory failed : " + response.getErrorMsg());
			return vendorProductCode;
		}
		return "";
	}
}
