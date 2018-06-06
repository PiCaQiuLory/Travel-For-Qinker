package com.ss.pojo.trip;

import java.io.Serializable;

public class TravelResult implements Serializable{

	private int code;
	
	private Object message;
	

	public TravelResult() {
		super();
	}

	public TravelResult(int code, Object message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public static TravelResult buildOk(Object message) {
		return new TravelResult(200, message);
	}
	
	public static TravelResult buildError(int code,Object message) {
		return new TravelResult(code, message);
	}
}
