package com.ss.pojo.ctrip;

import java.time.LocalDateTime;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "GetTodoOrderListRequest")
public class GetTodoOrderListRequest extends RequestBase {
	
	@Element(required=false)
	private String StartDateTime;


public String getStartDateTime() {
	return StartDateTime;
 }

public void setStartDateTime(String startdatetime) {
StartDateTime = startdatetime;
}
	
	@Element(required=false)
	private String EndDateTime;


public String getEndDateTime() {
	return EndDateTime;
 }

public void setEndDateTime(String enddatetime) {
EndDateTime = enddatetime;
}
	
	public void SetStartDateTime(LocalDateTime start)
	{
		this.StartDateTime = start.toString();
	}
	
	public void SetEndDateTime(LocalDateTime end)
	{
		this.EndDateTime = end.toString();
	}
	
	public void SetVendorId(long vendorId)
	{
		super.SetVendorId(vendorId);
	}
	
	public void SetVendorToken(String vendorToken)
	{
		super.SetVendorToken(vendorToken);
	}
}
