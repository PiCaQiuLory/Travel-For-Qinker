package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Trip implements Serializable{
	
	private int id;
	
	private String title;
	
	private Date create_date;
	
	private String customer_name;
	
	private String customer_wechat;
	
	private String itemNumber;
	
	private Date travel_date;
	
	private String travelDateStr;
	
	private Date end_date;
	
	private String endDateStr;
	
	private String start_point;
	
	private String end_point;
	
	private int user_id;
	
	private int department_id;
	
	private String phoneNumber;
	
	private List<TripItemVo> tripItems;
	
	private List<Plane> plane;
	
	private Consumer consumer;
	
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Date getTravel_date() {
		return travel_date;
	}

	public void setTravel_date(Date travel_date) {
		this.travel_date = travel_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getStart_point() {
		return start_point;
	}

	public void setStart_point(String start_point) {
		this.start_point = start_point;
	}

	public String getEnd_point() {
		return end_point;
	}

	public void setEnd_point(String end_point) {
		this.end_point = end_point;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCustomer_wechat() {
		return customer_wechat;
	}

	public void setCustomer_wechat(String customer_wechat) {
		this.customer_wechat = customer_wechat;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public List<TripItemVo> getTripItems() {
		return tripItems;
	}

	public void setTripItems(List<TripItemVo> tripItems) {
		this.tripItems = tripItems;
	}

	public String getTravelDateStr() {
		return travelDateStr;
	}

	public void setTravelDateStr(String travelDateStr) {
		this.travelDateStr = travelDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Plane> getPlane() {
		return plane;
	}

	public void setPlane(List<Plane> plane) {
		this.plane = plane;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	
	
	
}
