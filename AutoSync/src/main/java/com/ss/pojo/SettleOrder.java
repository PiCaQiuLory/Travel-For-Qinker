package com.ss.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SettleOrder {

	private Integer id;
	
	private String itemNumber;
	
	private Float subtotal;
	
	private Float subtotalGUI;
	
	private Integer length;
	
	private Integer lengthGUI;
	
	private String reason;
	
	private String user;
	
	private String manFlat;
	
	private String nickname;
	
	private Date startDate;

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

	public Float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Float getSubtotalGUI() {
		return subtotalGUI;
	}

	public void setSubtotalGUI(Float subtotalGUI) {
		this.subtotalGUI = subtotalGUI;
	}

	public Integer getLengthGUI() {
		return lengthGUI;
	}

	public void setLengthGUI(Integer lengthGUI) {
		this.lengthGUI = lengthGUI;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getManFlat() {
		return manFlat;
	}

	public void setManFlat(String manFlat) {
		this.manFlat = manFlat;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
}
