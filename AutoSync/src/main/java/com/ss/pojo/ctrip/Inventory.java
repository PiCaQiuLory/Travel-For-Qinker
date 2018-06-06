package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Root
public class Inventory {
@Element(required=false)
private String StartDate ;


public String getStartDate() {
	return StartDate;
 }

@ElementList(required=false,entry="dateTime")
private List<String> DateList;

public List<String> getDateList() {
	return DateList;
}

public void setDateList(List<String> dateList) {
	DateList = dateList;
}

public void setStartDate(String startdate) {
StartDate = startdate;
}

@Element(required=false)
private String EndDate ;


public String getEndDate() {
	return EndDate;
 }

public void setEndDate(String enddate) {
EndDate = enddate;
}

@Element(required=false)
private String DayOfWeek ;


public String getDayOfWeek() {
	return DayOfWeek;
 }

public void setDayOfWeek(String dayofweek) {
DayOfWeek = dayofweek;
}

@ElementList(required=false,entry="PackageInventoryInfo")
private List<PackageInventoryInfo> PackageInventoryInfoList ;


public List<PackageInventoryInfo> getPackageInventoryInfoList() {
	return PackageInventoryInfoList;
 }

public void setPackageInventoryInfoList(List<PackageInventoryInfo> packageinventoryinfolist) {
PackageInventoryInfoList = packageinventoryinfolist;
}

}
