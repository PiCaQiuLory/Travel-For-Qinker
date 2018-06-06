package com.ss.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderApplyMerge implements Serializable{

	private Integer id;
	
	private String mergeNumber;
	
	private String name;
	
	private Float subTotal;
	
	private Integer mergeUserId;
	
	private Date mergeTime;
	
	private String payNumber;
	
	private Date payTime;
	
	private String payTimeStr;
	
	private Integer pay_way;
	
	private Integer auditorId;
	
	private Float billCost;
	
	private String billStatus;
	
	private Date billTime;
	
	private String billTimeStr;
	
	private String mergeTimeStr;
	
	private String status;
	
	private String nickname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getBillTimeStr() {
		return billTimeStr;
	}

	public void setBillTimeStr(String billTimeStr) {
		this.billTimeStr = billTimeStr;
	}

	public String getMergeNumber() {
		return mergeNumber;
	}

	public void setMergeNumber(String mergeNumber) {
		this.mergeNumber = mergeNumber;
	}

	
	public String getMergeTimeStr() {
		return mergeTimeStr;
	}

	public void setMergeTimeStr(String mergeTimeStr) {
		this.mergeTimeStr = mergeTimeStr;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public Integer getMergeUserId() {
		return mergeUserId;
	}

	public void setMergeUserId(Integer mergeUserId) {
		this.mergeUserId = mergeUserId;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Float getBillCost() {
		return billCost;
	}

	public void setBillCost(Float billCost) {
		this.billCost = billCost;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getMergeTime() {
		return mergeTime;
	}

	public void setMergeTime(Date mergeTime) {
		this.mergeTime = mergeTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getPay_way() {
		return pay_way;
	}

	public void setPay_way(Integer pay_way) {
		this.pay_way = pay_way;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
	
	
	
}
