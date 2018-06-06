package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Root
public class InventoryType {
@Element(required=false)
private String StartDate ;
;

public String getStartDate() {	return StartDate;}

public void setStartDate(String startdate) {StartDate = startdate;}
@Element(required=false)
private String EndDate ;
;

public String getEndDate() {	return EndDate;}

public void setEndDate(String enddate) {EndDate = enddate;}
@Element(required=false)
private String DayOfWeek ;
;

public String getDayOfWeek() {	return DayOfWeek;}

public void setDayOfWeek(String dayofweek) {DayOfWeek = dayofweek;}
@Element(required=false)
private List<PackageInventoryInfo> PackageInventoryInfoList ;
;

public List<PackageInventoryInfo> getPackageInventoryInfoList() {	return PackageInventoryInfoList;}

public void setPackageInventoryInfoList(List<PackageInventoryInfo> packageinventoryinfolist) {PackageInventoryInfoList = packageinventoryinfolist;}
}
