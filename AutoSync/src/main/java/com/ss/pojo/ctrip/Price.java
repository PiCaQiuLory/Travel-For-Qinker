package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Price {
@Element(required=false,name="Price")
private String StartDate ;

@ElementList(required=false,entry="dateTime")
private List<String> DateList;



public List<String> getDateTime() {
	return DateList;
}

public void setDateTime(List<String> dateTime) {
	this.DateList = dateTime;
}

public String getStartDate() {
	return StartDate;
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

@ElementList(required=false,name="PackagePriceInfoList",entry="PackagePriceInfo")
private List<PackagePriceInfo> PackagePriceInfoList ;


public List<PackagePriceInfo> getPackagePriceInfoList() {
	return PackagePriceInfoList;
 }

public void setPackagePriceInfoList(List<PackagePriceInfo> packagepriceinfolist) {
PackagePriceInfoList = packagepriceinfolist;
}

@ElementList(required=false)
private List<OptionPriceInfo> OptionPriceInfoList ;


public List<OptionPriceInfo> getOptionPriceInfoList() {
	return OptionPriceInfoList;
 }

public void setOptionPriceInfoList(List<OptionPriceInfo> optionpriceinfolist) {
OptionPriceInfoList = optionpriceinfolist;
}

}
