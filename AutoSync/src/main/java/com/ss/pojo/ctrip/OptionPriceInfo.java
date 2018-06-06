package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class OptionPriceInfo {
@Element(required=false)
private String VendorOptionCode ;


public String getVendorOptionCode() {
	return VendorOptionCode;
 }

public void setVendorOptionCode(String vendoroptioncode) {
VendorOptionCode = vendoroptioncode;
}

@Element(required=false)
private double CostPrice ;


public double getCostPrice() {
	return CostPrice;
 }

public void setCostPrice(double costprice) {
CostPrice = costprice;
}

@Element(required=false)
private double SalePrice ;


public double getSalePrice() {
	return SalePrice;
 }

public void setSalePrice(double saleprice) {
SalePrice = saleprice;
}

}
