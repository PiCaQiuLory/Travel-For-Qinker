package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Order {
	@Element(required=false)
	private long OrderId;


public long getOrderId() {
	return OrderId;
 }

public void setOrderId(long orderid) {
OrderId = orderid;
}
	
	@Element(required=false)
	private OrderAction Action;


public OrderAction getAction() {
	return Action;
 }

public void setAction(OrderAction action) {
Action = action;
}
	
	@Element(required=false)
    private String VendorProductCode;


public String getVendorProductCode() {
	return VendorProductCode;
 }

public void setVendorProductCode(String vendorproductcode) {
VendorProductCode = vendorproductcode;
}
	
	@Element(required=false)
    private String DepartureDate;


public String getDepartureDate() {
	return DepartureDate;
 }

public void setDepartureDate(String departuredate) {
DepartureDate = departuredate;
}
	
	@Element(required=false)
	private OrderInfo OrderInfo;


public OrderInfo getOrderInfo() {
	return OrderInfo;
 }

public void setOrderInfo(OrderInfo orderinfo) {
OrderInfo = orderinfo;
}
	
	@ElementList(required=false)
	private List<Change> ChangeList;


public List<Change> getChangeList() {
	return ChangeList;
 }

public void setChangeList(List<Change> changelist) {
ChangeList = changelist;
}
	
	@Element(required=false)
	private String MessageId;


public String getMessageId() {
	return MessageId;
 }

public void setMessageId(String messageid) {
MessageId = messageid;
}
	
	public String GetOrderId()
	{
		return Long.toString(this.OrderId);
	}
}
