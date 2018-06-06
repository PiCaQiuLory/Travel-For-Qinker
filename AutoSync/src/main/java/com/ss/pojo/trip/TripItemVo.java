package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.List;

public class TripItemVo implements Serializable{

	private int id;

	private int day;

	private String title;
	
	private TravelReservation travelReservation;

	
	private List<TripItemDescription> itemDescriptions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TripItemDescription> getItemDescriptions() {
		return itemDescriptions;
	}

	public void setItemDescriptions(List<TripItemDescription> itemDescriptions) {
		this.itemDescriptions = itemDescriptions;
	}

	public TravelReservation getTravelReservation() {
		return travelReservation;
	}

	public void setTravelReservation(TravelReservation travelReservation) {
		this.travelReservation = travelReservation;
	}

	
	
	
}
