package com.ss.pojo;

import java.io.Serializable;

/**
 * 
 * @author Lory
 * Description : ajax消息通用响应消息
 */
public class ModelResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 910295430075753011L;
	
	public ModelResult() {

	}

	public ModelResult(int code, Object message) {
		super();
		this.code = code;
		this.message = message;
	}

	//响应代码
	private int code;
	
	//响应内容
	private Object message;

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
	
	public static ModelResult buildOk(Object message) {
		return new ModelResult(200,message);
	}
	
	public static ModelResult buildError(int code,Object message) {
		return new ModelResult(code,message);
	}

}
