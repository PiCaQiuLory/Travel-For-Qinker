package com.ss.pojo.app;

import java.io.Serializable;

public class AppWechatName implements Serializable{

	private Integer id;
	private String wechatName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	
	
}
