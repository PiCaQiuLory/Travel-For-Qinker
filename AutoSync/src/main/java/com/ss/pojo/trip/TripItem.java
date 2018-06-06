package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TripItem implements Serializable{
	
	private int id;
	
	private int day;
	
	private String title;
	
	private List<TicketEntity> ticketEntities;
	private List<BusTicketEntity> busTicketEntities;
	private List<Schedule> scheduleEntities;
	private List<CharteredEntity> charteredEntities;
	
	
	private int trip_id;

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

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public List<TicketEntity> getTicketEntities() {
		return ticketEntities;
	}

	public void setTicketEntities(List<TicketEntity> ticketEntities) {
		this.ticketEntities = ticketEntities;
	}

	public List<BusTicketEntity> getBusTicketEntities() {
		return busTicketEntities;
	}

	public void setBusTicketEntities(List<BusTicketEntity> busTicketEntities) {
		this.busTicketEntities = busTicketEntities;
	}

	public List<Schedule> getScheduleEntities() {
		return scheduleEntities;
	}

	public void setScheduleEntities(List<Schedule> scheduleEntities) {
		this.scheduleEntities = scheduleEntities;
	}

	public List<CharteredEntity> getCharteredEntities() {
		return charteredEntities;
	}

	public void setCharteredEntities(List<CharteredEntity> charteredEntities) {
		this.charteredEntities = charteredEntities;
	}
	
	

}
