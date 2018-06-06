package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class RequestBase {
	@Element(required=false)
	private RequestHeaderType RequestHeader;


public RequestHeaderType getRequestHeader() {
	return RequestHeader;
 }

public void setRequestHeader(RequestHeaderType requestheader) {
RequestHeader = requestheader;
}
	
	public void SetVendorId(long vendorId)
	{
		if (RequestHeader == null)
				this.RequestHeader = new RequestHeaderType();
		this.RequestHeader.SetVendorId(vendorId);
	}
	
	public void SetVendorToken(String vendorToken)
	{
		if (RequestHeader == null)
			this.RequestHeader = new RequestHeaderType();
		this.RequestHeader.SetVendorToken(vendorToken);
	}
}
