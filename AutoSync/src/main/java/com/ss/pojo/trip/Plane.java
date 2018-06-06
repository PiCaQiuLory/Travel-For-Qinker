package com.ss.pojo.trip;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Plane implements Serializable{
	
	private int id;
	
	private Date to_date;
	
	private String to_date_str;
	
	private String to_number;
	
	private String to_desc;
	
	private String to_start_end;
	
	private List<Date> to_start_end_date;
	
	private Date land_date;
	
	private String land_date_str;
	
	private String land_number;
	
	private String land_desc;
	
	private String land_start_end;
	
	private List<Date> land_start_end_date;
	
	private Date back_date;
	
	private String back_date_str;
	
	private String back_number;
	
	private String back_desc;
	
	private String back_start_end;
	
	private List<Date> back_start_end_date;
	
	private int trip_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public String getTo_number() {
		return to_number;
	}

	public void setTo_number(String to_number) {
		this.to_number = to_number;
	}

	public String getTo_desc() {
		return to_desc;
	}

	public void setTo_desc(String to_desc) {
		this.to_desc = to_desc;
	}

	public String getTo_start_end() {
		return to_start_end;
	}

	public void setTo_start_end(String to_start_end) {
		this.to_start_end = to_start_end;
	}

	public Date getLand_date() {
		return land_date;
	}

	public void setLand_date(Date land_date) {
		this.land_date = land_date;
	}

	public String getLand_number() {
		return land_number;
	}

	public void setLand_number(String land_number) {
		this.land_number = land_number;
	}

	public String getLand_desc() {
		return land_desc;
	}

	public void setLand_desc(String land_desc) {
		this.land_desc = land_desc;
	}

	public String getLand_start_end() {
		return land_start_end;
	}

	public void setLand_start_end(String land_start_end) {
		this.land_start_end = land_start_end;
	}

	public Date getBack_date() {
		return back_date;
	}

	public void setBack_date(Date back_date) {
		this.back_date = back_date;
	}

	public String getBack_number() {
		return back_number;
	}

	public void setBack_number(String back_number) {
		this.back_number = back_number;
	}

	public String getBack_desc() {
		return back_desc;
	}

	public void setBack_desc(String back_desc) {
		this.back_desc = back_desc;
	}

	public String getBack_start_end() {
		return back_start_end;
	}

	public void setBack_start_end(String back_start_end) {
		this.back_start_end = back_start_end;
	}

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public List<Date> getTo_start_end_date() {
		return to_start_end_date;
	}

	public void setTo_start_end_date(List<Date> to_start_end_date) {
		this.to_start_end_date = to_start_end_date;
	}

	public List<Date> getLand_start_end_date() {
		return land_start_end_date;
	}

	public void setLand_start_end_date(List<Date> land_start_end_date) {
		this.land_start_end_date = land_start_end_date;
	}

	public List<Date> getBack_start_end_date() {
		return back_start_end_date;
	}

	public void setBack_start_end_date(List<Date> back_start_end_date) {
		this.back_start_end_date = back_start_end_date;
	}

	

	public String getTo_date_str() {
		return to_date_str;
	}

	public void setTo_date_str(String to_date_str) {
		this.to_date_str = to_date_str;
	}

	public String getLand_date_str() {
		return land_date_str;
	}

	public void setLand_date_str(String land_date_str) {
		this.land_date_str = land_date_str;
	}

	public String getBack_date_str() {
		return back_date_str;
	}

	public void setBack_date_str(String back_date_str) {
		this.back_date_str = back_date_str;
	}
	
	

}
