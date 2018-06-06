package com.ss.pojo.app;

import java.io.Serializable;
import java.util.List;

public class AppDataVo implements Serializable{
	
	private String iemi;
	
	private List<AppData> appData;

	
	
	

	public String getIemi() {
		return iemi;
	}

	public void setIemi(String iemi) {
		this.iemi = iemi;
	}

	public List<AppData> getAppData() {
		return appData;
	}

	public void setAppData(List<AppData> appData) {
		this.appData = appData;
	}
	
	

}
