package com.ss.pojo.spider;

import java.io.Serializable;

public class AlipaySettle implements Serializable{
	
	private Integer id;
	private String inDate;//入账时间
	private String transactionNumber;//支付宝交易账号
	private String payNumber;//交易流水号
	private String orderNumber;//商户订单号
	private String payType;//账务类型
	private String income;//收入
	private String expenditure;//支出
	private String balance;//账户余额
	private String payway;//付款方式
	private String otherAccount;//对方账户
	private String otherName;//对方名称
	private String goodsName;//商品名称
	private String remark;//备注
	private String price;
	private String reason = "正确";//原因
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getPayNumber() {
		return payNumber;
	}
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getOtherAccount() {
		return otherAccount;
	}
	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
