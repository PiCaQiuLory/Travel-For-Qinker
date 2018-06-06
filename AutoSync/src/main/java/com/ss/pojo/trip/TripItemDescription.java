package com.ss.pojo.trip;

import java.io.Serializable;

public class TripItemDescription implements Serializable{

	private int id;
	
	private String description;
	
	private int sort_id;
	
	private int trip_item_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSort_id() {
		return sort_id;
	}

	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}

	public int getTrip_item_id() {
		return trip_item_id;
	}

	public void setTrip_item_id(int trip_item_id) {
		this.trip_item_id = trip_item_id;
	}
	
	
}
