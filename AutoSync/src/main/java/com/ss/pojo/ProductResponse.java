package com.ss.pojo;

import java.sql.Timestamp;

public class ProductResponse {
	private long id;
	
	private long sourceProductId;
	
	private Timestamp tDate;
	
	private Double tPrice;
	
	private Double tSinglePrice;
	
	private Double tChildPrice;
	
	private int stock;
	
	private Timestamp lastSyncDatetime;
	
	private String sendInd;
	
	private String source;
	
	private String sourceUrl;

	private String flightNote;

	public String getFlightNote() {
		return flightNote;
	}

	public void setFlightNote(String flightNote) {
		this.flightNote = flightNote;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceProductId() {
		return sourceProductId;
	}

	public void setSourceProductId(long sourceProductId) {
		this.sourceProductId = sourceProductId;
	}

	public Timestamp gettDate() {
		return tDate;
	}

	public void settDate(Timestamp tDate) {
		this.tDate = tDate;
	}

	public Double gettPrice() {
		return tPrice;
	}

	public void settPrice(Double tPrice) {
		this.tPrice = tPrice;
	}

	public Double gettSinglePrice() {
		return tSinglePrice;
	}

	public void settSinglePrice(Double tSinglePrice) {
		this.tSinglePrice = tSinglePrice;
	}

	public Double gettChildPrice() {
		return tChildPrice;
	}

	public void settChildPrice(Double tChildPrice) {
		this.tChildPrice = tChildPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Timestamp getLastSyncDatetime() {
		return lastSyncDatetime;
	}

	public void setLastSyncDatetime(Timestamp lastSyncDatetime) {
		this.lastSyncDatetime = lastSyncDatetime;
	}

	public String getSendInd() {
		return sendInd;
	}

	public void setSendInd(String sendInd) {
		this.sendInd = sendInd;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	
}
