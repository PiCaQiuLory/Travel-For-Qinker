package com.ss.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SourceProductResponse {
	private long id;
	
	private String source;
	
	private String url;
	
	private String syncStatus;
	
	private Timestamp syncEndDate;
	
	private Timestamp lastSyncDatetime;
	
	private Timestamp syncTime1;
	
	private Timestamp syncTime2;
	
	private Timestamp syncTime3;
	
	private Timestamp syncTime4;
	
	private Timestamp syncTime5;
	
	private Timestamp syncTime6;
	
	private String mStatus;
	
	private Integer needSync;
	
	private String reasonCode;
	
	private String updatedBy;
	
	private Timestamp updatedDt;
	
	private List<TargetProductResponse> targetList;

	private List<DynamicProduct> dynamicProducts;
	private String country;
	private String city;
	private String days;
	private String productType;

	private String action;
	private String customField;

	private String airportFrom;
	private String airportTo;
	private String flightTimeFrom;
	private String flightTimeTo;
	private String flightTimeFrom2;
	private String flightTimeTo2;
	private Integer stock;
	private Integer immediate;

	private Float increasePer;

	private Float decreasePer;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<DynamicProduct> getDynamicProducts() {
		return dynamicProducts;
	}

	public void setDynamicProducts(List<DynamicProduct> dynamicProducts) {
		this.dynamicProducts = dynamicProducts;
	}

	public Float getDecreasePer() {
		return decreasePer;
	}

	public void setDecreasePer(Float decreasePer) {
		this.decreasePer = decreasePer;
	}

	public Float getIncreasePer() {
		return increasePer;
	}

	public void setIncreasePer(Float increasePer) {
		this.increasePer = increasePer;
	}

	public Integer getImmediate() {
		return immediate;
	}

	public void setImmediate(Integer immediate) {
		this.immediate = immediate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	public String getAirportTo() {
		return airportTo;
	}

	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}

	public String getFlightTimeFrom2() {
		return flightTimeFrom2;
	}

	public void setFlightTimeFrom2(String flightTimeFrom2) {
		this.flightTimeFrom2 = flightTimeFrom2;
	}

	public String getFlightTimeFrom() {
		return flightTimeFrom;
	}

	public void setFlightTimeFrom(String flightTimeFrom) {
		this.flightTimeFrom = flightTimeFrom;
	}

	public String getFlightTimeTo2() {
		return flightTimeTo2;
	}

	public void setFlightTimeTo2(String flightTimeTo2) {
		this.flightTimeTo2 = flightTimeTo2;
	}

	public String getFlightTimeTo() {
		return flightTimeTo;
	}

	public void setFlightTimeTo(String flightTimeTo) {
		this.flightTimeTo = flightTimeTo;
	}

	public String getCustomField() {
		return customField;
	}

	public void setCustomField(String customField) {
		this.customField = customField;
	}

	public long getId() {
		return id;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public Integer getNeedSync() {
		return needSync;
	}

	public void setNeedSync(Integer needSync) {
		this.needSync = needSync;
	}

	

	public Timestamp getSyncEndDate() {
		return syncEndDate;
	}

	public void setSyncEndDate(Timestamp syncEndDate) {
		this.syncEndDate = syncEndDate;
	}

	public Timestamp getLastSyncDatetime() {
		return lastSyncDatetime;
	}

	public void setLastSyncDatetime(Timestamp lastSyncDatetime) {
		this.lastSyncDatetime = lastSyncDatetime;
	}

	public Timestamp getSyncTime2() {
		return syncTime2;
	}

	public void setSyncTime2(Timestamp syncTime2) {
		this.syncTime2 = syncTime2;
	}

	public Timestamp getSyncTime3() {
		return syncTime3;
	}

	public void setSyncTime3(Timestamp syncTime3) {
		this.syncTime3 = syncTime3;
	}

	public Timestamp getSyncTime4() {
		return syncTime4;
	}

	public void setSyncTime4(Timestamp syncTime4) {
		this.syncTime4 = syncTime4;
	}

	public Timestamp getSyncTime5() {
		return syncTime5;
	}

	public void setSyncTime5(Timestamp syncTime5) {
		this.syncTime5 = syncTime5;
	}

	public Timestamp getSyncTime6() {
		return syncTime6;
	}

	public void setSyncTime6(Timestamp syncTime6) {
		this.syncTime6 = syncTime6;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public void setSyncTime1(Timestamp syncTime1) {
		this.syncTime1 = syncTime1;
	}

	public Date getSyncTime1() {
		return syncTime1;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public List<TargetProductResponse> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<TargetProductResponse> targetList) {
		this.targetList = targetList;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
	
}
