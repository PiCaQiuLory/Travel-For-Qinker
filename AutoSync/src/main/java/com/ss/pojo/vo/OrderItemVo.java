package com.ss.pojo.vo;

import java.io.Serializable;
import java.util.Date;

public class OrderItemVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -742245995643400715L;
	
	private Integer id;
	
	private int orderItemId;
	
	private Date applyTime;
	
	private String applyTimeStr;
	
	private Float total;
	
	private String type;

	private String orderNo;
	
	private String name;
	
	private String category;
	
	private String status; 
	
	private Date payTime;
	
	private String payTimeStr;
	
	private Integer payWay;
	
	private String payNumber;
	
	private String itemNumber;
	
	private Integer userId;
	
	private Integer auditorId;
	
	private Date billDate;
	
	private String billDateStr;
	
	private Float billCost;
	
	private String billStatus;
	
	private Date useDate;
	
	private String useDateStr;
	
	private String formatDate;
	
	private String userNickName;
	
	private String auditorName;
	
	private long provider_id;
	
	private Float subTotal;
	
	
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

	public String getBillDateStr() {
		return billDateStr;
	}

	public void setBillDateStr(String billDateStr) {
		this.billDateStr = billDateStr;
	}

	public String getUseDateStr() {
		return useDateStr;
	}

	public void setUseDateStr(String useDateStr) {
		this.useDateStr = useDateStr;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public long getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(long provider_id) {
		this.provider_id = provider_id;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}
	
	
	
}
