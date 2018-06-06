package com.ss.pojo.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AppResult implements Serializable{

	private Integer code;
	private String msg;
	private Object data;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public AppResult(){}

	public AppResult(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	//操作成功
	public static AppResult buildOK(Object data) {
		AppResult res = new AppResult();
		res.setCode(200);
		res.setMsg("SUCCESS");
		res.setData(data);
		return res;
	}
	
	//系统异常
	public static AppResult buildError(Object data) {
		AppResult res = new AppResult();
		res.setCode(0);
		res.setMsg("FAILED");
		res.setData(data);
		return res;
	}
	
	//参数错误
	public static AppResult buildErrorParam(Object data) {
		AppResult res = new AppResult();
		res.setCode(303);
		res.setMsg("ERROR PARAMS");
		res.setData(data);
		return res;
	}
	
	public Map<String, Object> toMap(){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", data);
        return resultMap;
    }
	
}
