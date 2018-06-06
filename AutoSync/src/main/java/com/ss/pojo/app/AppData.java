package com.ss.pojo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 手机上传实体
 * @author 13162
 *
 */
public class AppData implements Serializable{
	
	private Integer id;
	private String wechatId;
	private String timestamp;
	private Date time;
	private String content;
	private Integer appWechatId;
	private Integer type;
	private Integer owner;
	private String filename;
	private Integer roomId;
	private Integer wechatNameId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getAppWechatId() {
		return appWechatId;
	}
	public void setAppWechatId(Integer appWechatId) {
		this.appWechatId = appWechatId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public Integer getWechatNameId() {
		return wechatNameId;
	}
	public void setWechatNameId(Integer wechatNameId) {
		this.wechatNameId = wechatNameId;
	}

	
}
