package com.ss.pojo.trip;

import java.io.Serializable;

public class Consumer implements Serializable{
	
	private int id;
	private String content;
	private int trip_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	
	

}
