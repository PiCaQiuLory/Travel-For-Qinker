package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ResponseBase {
	
	@Element(required=false)
	private String ErrorCode;


public String getErrorCode() {
	return ErrorCode;
 }

public void setErrorCode(String errorcode) {
ErrorCode = errorcode;
}
	
	@Element(required=false)
	private String ErrorMsg;


public String getErrorMsg() {
	return ErrorMsg;
 }

public void setErrorMsg(String errormsg) {
ErrorMsg = errormsg;
}
	
	public String GetErrorCode()
	{
		return this.ErrorCode;
	}
}
