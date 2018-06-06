package com.ss.pojo.trip;

import java.io.Serializable;

public class OrderPlatform implements Serializable{
	
	private String itemNumber;
	private String customerName;
	private String getOrderTimer;
	private String user;
	private String remark;
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getGetOrderTimer() {
		return getOrderTimer;
	}
	public void setGetOrderTimer(String getOrderTimer) {
		this.getOrderTimer = getOrderTimer;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	

}
