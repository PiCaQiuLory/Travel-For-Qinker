package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class OptionInfo {
	@Element(required=false)
	public String VendorOptionCode;
	@Element(required=false)
	private String Currency;


public String getCurrency() {
	return Currency;
 }

public void setCurrency(String currency) {
Currency = currency;
}
	@Element(required=false)
	private double ExchangeRate;


public double getExchangeRate() {
	return ExchangeRate;
 }

public void setExchangeRate(double exchangerate) {
ExchangeRate = exchangerate;
}
	@Element(required=false)
	private double CostPrice;


public double getCostPrice() {
	return CostPrice;
 }

public void setCostPrice(double costprice) {
CostPrice = costprice;
}
	@Element(required=false)
	private int Quantity;


public int getQuantity() {
	return Quantity;
 }

public void setQuantity(int quantity) {
Quantity = quantity;
}
	@ElementList(required=false)
	private List<String> TravelerIdList;


public List<String> getTravelerIdList() {
	return TravelerIdList;
 }

public void setTravelerIdList(List<String> traveleridlist) {
TravelerIdList = traveleridlist;
}

}
