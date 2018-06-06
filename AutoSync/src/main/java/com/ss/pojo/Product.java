package com.ss.pojo;

import java.util.Date;

public class Product {
	private long id;
	
	private String sourceId;
	
	private Date tDate;
	
	private Double tPrice;
	
	private Double tSprice;
	
	private Double tCPrice;
	
	private int stock;
	
	private Date lastSyncDt;

	private String sendInd;
	
	private Double limit;

	private String flightNote;

	public String getFlightNote() {
		return flightNote;
	}

	public void setFlightNote(String flightNote) {
		this.flightNote = flightNote;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public String getSendInd() {
		return sendInd;
	}

	public void setSendInd(String sendInd) {
		this.sendInd = sendInd;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getLastSyncDt() {
		return lastSyncDt;
	}

	public void setLastSyncDt(Date lastSyncDt) {
		this.lastSyncDt = lastSyncDt;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


	
	
}
