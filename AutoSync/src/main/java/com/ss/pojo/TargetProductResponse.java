package com.ss.pojo;

import java.sql.Timestamp;

public class TargetProductResponse {
	private long id;
	
	private long sourceProductId;
	
	private String targetSource;
	
	private String targetProductId;
	
	private String priceDelta;
	
	private String sPriceDelta;
	
	private String childPriceDelta;
	
	private Double defaultPriceLimit;
	
	private String syncStauts;
	
	private Timestamp syncEndDate;
	
	private Timestamp lastSyncDatetime;
	
	private String syncTime1;
	
	private String syncTime2;
	
	private String syncTime3;
	
	private String syncTime4;
	
	private String syncTime5;
	
	private String syncTime6;
	
	private Integer targetConfigure;
	
	private String mStatus;
	
	private Integer needSync;
	
	private String reasonCode;
	
	private String updatedBy;
	
	private Timestamp updatedDt;
	
	private SourceProductResponse sourceProductResponse;

	private String action;

	private Double coefficient;

	private String customField;

	private String packageName;

	private String vendorId;
	
	private int offset;
	
	private int dueDate;
	
	private Double ratio;

	private Double baseAmount;

	private String mainFlag;

	private long productOwnerId;

	private String productOwnerName;

	private String notes;

	private Double defaultChildPrice;
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProductOwnerName() {
		return productOwnerName;
	}

	public void setProductOwnerName(String productOwnerName) {
		this.productOwnerName = productOwnerName;
	}

	public long getProductOwnerId() {
		return productOwnerId;
	}

	public void setProductOwnerId(long productOwnerId) {
		this.productOwnerId = productOwnerId;
	}

	public String getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}

	public Double getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(Double baseAmount) {
		this.baseAmount = baseAmount;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCustomField() {
		return customField;
	}

	public void setCustomField(String customField) {
		this.customField = customField;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceProductId() {
		return sourceProductId;
	}

	public void setSourceProductId(long sourceProductId) {
		this.sourceProductId = sourceProductId;
	}

	public String getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(String targetSource) {
		this.targetSource = targetSource;
	}

	public String getTargetProductId() {
		return targetProductId;
	}

	public void setTargetProductId(String targetProductId) {
		this.targetProductId = targetProductId;
	}

	public String getPriceDelta() {
		return priceDelta;
	}

	public void setPriceDelta(String priceDelta) {
		this.priceDelta = priceDelta;
	}

	public String getsPriceDelta() {
		return sPriceDelta;
	}

	public void setsPriceDelta(String sPriceDelta) {
		this.sPriceDelta = sPriceDelta;
	}

	public String getChildPriceDelta() {
		return childPriceDelta;
	}

	public void setChildPriceDelta(String childPriceDelta) {
		this.childPriceDelta = childPriceDelta;
	}

	public Double getDefaultPriceLimit() {
		return defaultPriceLimit;
	}

	public void setDefaultPriceLimit(Double defaultPriceLimit) {
		this.defaultPriceLimit = defaultPriceLimit;
	}

	public String getSyncStauts() {
		return syncStauts;
	}

	public void setSyncStauts(String syncStauts) {
		this.syncStauts = syncStauts;
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

	public String getSyncTime1() {
		return syncTime1;
	}

	public void setSyncTime1(String syncTime1) {
		this.syncTime1 = syncTime1;
	}

	public String getSyncTime2() {
		return syncTime2;
	}

	public void setSyncTime2(String syncTime2) {
		this.syncTime2 = syncTime2;
	}

	public String getSyncTime3() {
		return syncTime3;
	}

	public void setSyncTime3(String syncTime3) {
		this.syncTime3 = syncTime3;
	}

	public String getSyncTime4() {
		return syncTime4;
	}

	public void setSyncTime4(String syncTime4) {
		this.syncTime4 = syncTime4;
	}

	public String getSyncTime5() {
		return syncTime5;
	}

	public void setSyncTime5(String syncTime5) {
		this.syncTime5 = syncTime5;
	}

	public String getSyncTime6() {
		return syncTime6;
	}

	public void setSyncTime6(String syncTime6) {
		this.syncTime6 = syncTime6;
	}

	public Integer getTargetConfigure() {
		return targetConfigure;
	}

	public void setTargetConfigure(Integer targetConfigure) {
		this.targetConfigure = targetConfigure;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public Integer getNeedSync() {
		return needSync;
	}

	public void setNeedSync(Integer needSync) {
		this.needSync = needSync;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public SourceProductResponse getSourceProductResponse() {
		return sourceProductResponse;
	}

	public void setSourceProductResponse(SourceProductResponse sourceProductResponse) {
		this.sourceProductResponse = sourceProductResponse;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getDueDate() {
		return dueDate;
	}

	public void setDueDate(int dueDate) {
		this.dueDate = dueDate;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public Double getDefaultChildPrice() {
		return defaultChildPrice;
	}

	public void setDefaultChildPrice(Double defaultChildPrice) {
		this.defaultChildPrice = defaultChildPrice;
	}


	
	
}
