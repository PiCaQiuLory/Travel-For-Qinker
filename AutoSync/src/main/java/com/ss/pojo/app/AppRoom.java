package com.ss.pojo.app;

import java.io.Serializable;

public class AppRoom implements Serializable{

	private Integer id;
	private String roomName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
}
