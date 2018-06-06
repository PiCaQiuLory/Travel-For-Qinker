package com.ss.pojo;

import java.util.Date;

public class SettleOrderVo {

	private String itemNumber;
	
	private String platform;
	
	private Integer length;
	
	private Float sub_total;
	
	private String date;
	
	private Date dateFormat;
	
	private String user;
	
	private Float[] totals;

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Float getSub_total() {
		return sub_total;
	}

	public void setSub_total(Float sub_total) {
		this.sub_total = sub_total;
	}

	public Float[] getTotals() {
		return totals;
	}

	public void setTotals(Float[] totals) {
		this.totals = totals;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(Date dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	
}
