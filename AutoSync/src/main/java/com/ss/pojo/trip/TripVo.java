package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.List;

public class TripVo implements Serializable{

	private Trip trip;
	
	private List<TripItemVo> tripItems;

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public List<TripItemVo> getTripItems() {
		return tripItems;
	}

	public void setTripItems(List<TripItemVo> tripItems) {
		this.tripItems = tripItems;
	}

	
	
	
}
