package com.ss.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderApplyItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2032055012601601636L;
	
	private int id;
	
	private Date applyTime;
	
	private String applyTimeStr;
	
	private Float total;
	
	private String type;
	
	private String category;
	
	private String itemNumber;
	
	private int userId;
	
	private Date useDate;
	
	private String useDateStr;
	
	private int orderItemId;
	
	private int orderMergeId;
	
	private String orderNo;
	
	private String nickname;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderMergeId() {
		return orderMergeId;
	}

	public void setOrderMergeId(int orderMergeId) {
		this.orderMergeId = orderMergeId;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getUseDateStr() {
		return useDateStr;
	}

	public void setUseDateStr(String useDateStr) {
		this.useDateStr = useDateStr;
	}
	
	

}
