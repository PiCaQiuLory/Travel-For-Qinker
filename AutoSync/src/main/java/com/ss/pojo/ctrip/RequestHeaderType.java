package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class RequestHeaderType {
	
	@Element(required=false)
	private long VendorId;


public long getVendorId() {
	return VendorId;
 }

public void setVendorId(long vendorid) {
VendorId = vendorid;
}
	
	@Element(required=false)
	private String VendorToken;


public String getVendorToken() {
	return VendorToken;
 }

public void setVendorToken(String vendortoken) {
VendorToken = vendortoken;
}
	
	public void SetVendorId(long vendorId)
	{
		this.VendorId = vendorId;
	}
	
	public void SetVendorToken(String vendorToken)
	{
		this.VendorToken = vendorToken;
	}
}