package com.ss.pojo.vo;

import java.io.Serializable;
import java.util.Date;

public class ApplyReimbursement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7145532848335641928L;
	private int id;
	
	private String event;
	
	private Date applyTime;
	
	private Float cost;
	
	private String nickName;
	
	private int userId;
	
	private Integer auditorId;
	
	private String auditorName;
	
	private Date auditorDate;
	
	private String status;
	
	private String applyTimeStr;
	
	private String auditorDateStr;
	
	private Float subTotal;
	
	private String category;
	
	private String department;
	
	
	
	
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public Date getAuditorDate() {
		return auditorDate;
	}

	public void setAuditorDate(Date auditorDate) {
		this.auditorDate = auditorDate;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getAuditorDateStr() {
		return auditorDateStr;
	}

	public void setAuditorDateStr(String auditorDateStr) {
		this.auditorDateStr = auditorDateStr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
