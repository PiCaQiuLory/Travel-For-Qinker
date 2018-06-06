package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PackageInfo {
	@Element(required=false)
	private String VendorPackageCode;


public String getVendorPackageCode() {
	return VendorPackageCode;
 }

public void setVendorPackageCode(String vendorpackagecode) {
VendorPackageCode = vendorpackagecode;
}
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
	private int AdultQuantity;


public int getAdultQuantity() {
	return AdultQuantity;
 }

public void setAdultQuantity(int adultquantity) {
AdultQuantity = adultquantity;
}
	@Element(required=false)
	private double AdultCostPrice;


public double getAdultCostPrice() {
	return AdultCostPrice;
 }

public void setAdultCostPrice(double adultcostprice) {
AdultCostPrice = adultcostprice;
}
	@Element(required=false)
	private int ChildQuantity;


public int getChildQuantity() {
	return ChildQuantity;
 }

public void setChildQuantity(int childquantity) {
ChildQuantity = childquantity;
}
	@Element(required=false)
	private double ChildCostPrice;


public double getChildCostPrice() {
	return ChildCostPrice;
 }

public void setChildCostPrice(double childcostprice) {
ChildCostPrice = childcostprice;
}
	@Element(required=false)
	private int SinglePersonQuantity;


public int getSinglePersonQuantity() {
	return SinglePersonQuantity;
 }

public void setSinglePersonQuantity(int singlepersonquantity) {
SinglePersonQuantity = singlepersonquantity;
}
	@Element(required=false)
	private double SinglePersonCostPrice;


public double getSinglePersonCostPrice() {
	return SinglePersonCostPrice;
 }

public void setSinglePersonCostPrice(double singlepersoncostprice) {
SinglePersonCostPrice = singlepersoncostprice;
}
	@Element(required=false)
	private int InfantQuantity;


public int getInfantQuantity() {
	return InfantQuantity;
 }

public void setInfantQuantity(int infantquantity) {
InfantQuantity = infantquantity;
}
}
