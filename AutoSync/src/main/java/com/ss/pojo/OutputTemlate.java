package com.ss.pojo;

import java.util.Date;
import java.util.List;


public class OutputTemlate {
	
	private String packageName;
	
	private String source;
	
	private String targetSource;
	
	private long id;
	
	private String targetProductId;
	
	private Double tPrice;
	
	private Double tSprice;
	
	private Double tCPrice; 
	
	private String priceDelta;
	
	private String sPriceDelta;
	
	private String cPriceDelta;
	
	private Double defaultPriceLimit;
	
	private int stock;

	private Date tDate;
	
	private String vendorId;
	
	private String token;
	
	private String apiUrl;
	
	private boolean isSent;
	
	private List<Date> tDateList;
	
	private String sourceId;
	
	private Double coefficient;
	
	private Integer offset;

	private Double ratio;
	
	private String mainFlag;
	
	private Double baseAmount;
	
	private Double defaultChildPrice;
	
	private List<OutputTemlate> packageList;
	
	
	public Double getDefaultChildPrice() {
		return defaultChildPrice;
	}

	public void setDefaultChildPrice(Double defaultChildPrice) {
		this.defaultChildPrice = defaultChildPrice;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(String targetSource) {
		this.targetSource = targetSource;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public List<Date> gettDateList() {
		return tDateList;
	}

	public void settDateList(List<Date> tDateList) {
		this.tDateList = tDateList;
	}

	public boolean isSent() {
		return isSent;
	}

	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date gettDate() {
		return tDate;
	}

	public void settDate(Date tDate) {
		this.tDate = tDate;
	}

	public String getTargetProductId() {
		return targetProductId;
	}

	public void setTargetProductId(String targetProductId) {
		this.targetProductId = targetProductId;
	}

	public Double gettPrice() {
		return tPrice;
	}

	public void settPrice(Double tPrice) {
		this.tPrice = tPrice;
	}

	public Double gettSprice() {
		return tSprice;
	}

	public void settSprice(Double tSprice) {
		this.tSprice = tSprice;
	}

	public Double gettCPrice() {
		return tCPrice;
	}

	public void settCPrice(Double tCPrice) {
		this.tCPrice = tCPrice;
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

	public String getcPriceDelta() {
		return cPriceDelta;
	}

	public void setcPriceDelta(String cPriceDelta) {
		this.cPriceDelta = cPriceDelta;
	}

	public Double getDefaultPriceLimit() {
		return defaultPriceLimit;
	}

	public void setDefaultPriceLimit(Double defaultPriceLimit) {
		this.defaultPriceLimit = defaultPriceLimit;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
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

	public List<OutputTemlate> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<OutputTemlate> packageList) {
		this.packageList = packageList;
	}

	
}
