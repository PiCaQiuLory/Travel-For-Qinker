package com.ss.pojo.app;

import java.io.Serializable;

public class AppFile implements Serializable{

	private Integer id;
	private String fileName;
	private String url;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
