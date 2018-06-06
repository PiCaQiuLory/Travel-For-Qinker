package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class OrderInfo {

	@Element(required=false)
	 private String BookingTime;


public String getBookingTime() {
	return BookingTime;
 }

public void setBookingTime(String bookingtime) {
BookingTime = bookingtime;
}
	
	@Element(required=false)
	 private PackageInfo PackageInfo;


public PackageInfo getPackageInfo() {
	return PackageInfo;
 }

public void setPackageInfo(PackageInfo packageinfo) {
PackageInfo = packageinfo;
}
	
	@ElementList(required=false)
	 private List<OptionInfo> OptionInfoList;


public List<OptionInfo> getOptionInfoList() {
	return OptionInfoList;
 }

public void setOptionInfoList(List<OptionInfo> optioninfolist) {
OptionInfoList = optioninfolist;
}
	
	@Element(required=false)
	 private BoardingInfo BoardingInfo;


public BoardingInfo getBoardingInfo() {
	return BoardingInfo;
 }

public void setBoardingInfo(BoardingInfo boardinginfo) {
BoardingInfo = boardinginfo;
}

	@ElementList(required=false)
	 private List<Traveler> TravelerList;


public List<Traveler> getTravelerList() {
	return TravelerList;
 }

public void setTravelerList(List<Traveler> travelerlist) {
TravelerList = travelerlist;
}

	@Element(required=false)
	 private boolean IsPaid;


public boolean getIsPaid() {
	return IsPaid;
 }

public void setIsPaid(boolean ispaid) {
IsPaid = ispaid;
}

	@Element(required=false)
	 private String Remark;


public String getRemark() {
	return Remark;
 }

public void setRemark(String remark) {
Remark = remark;
}
}
