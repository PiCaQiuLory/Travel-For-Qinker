package com.ss.pojo.spider;

import java.io.Serializable;
import java.math.BigDecimal;

public class SpiderDetail implements Serializable{
	
	private Integer id;//序号
	private String itemNumber;//订单号
	private String user;//账号
	private BigDecimal price;//金额
	private String remark;//备注
	private String wrongReason;//错误原因
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWrongReason() {
		return wrongReason;
	}
	public void setWrongReason(String wrongReason) {
		this.wrongReason = wrongReason;
	}
	
	
	

}
