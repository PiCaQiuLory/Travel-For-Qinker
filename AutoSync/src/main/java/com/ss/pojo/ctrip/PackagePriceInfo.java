package com.ss.pojo.ctrip;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PackagePriceInfo {
@Element(required=false)
private String VendorPackageCode ;


public String getVendorPackageCode() {
	return VendorPackageCode;
 }

public void setVendorPackageCode(String vendorpackagecode) {
VendorPackageCode = vendorpackagecode;
}

@Element(required=false)
private double AdultCostPrice ;


public double getAdultCostPrice() {
	return AdultCostPrice;
 }

public void setAdultCostPrice(double adultcostprice) {
AdultCostPrice = adultcostprice;
}

@Element(required=false)
private double AdultSalePrice ;


public double getAdultSalePrice() {
	return AdultSalePrice;
 }

public void setAdultSalePrice(double adultsaleprice) {
AdultSalePrice = adultsaleprice;
}

@Element(required=false)
private double ChildCostPrice ;


public double getChildCostPrice() {
	return ChildCostPrice;
 }

public void setChildCostPrice(double childcostprice) {
ChildCostPrice = childcostprice;
}

@Element(required=false)
private double ChildSalePrice ;


public double getChildSalePrice() {
	return ChildSalePrice;
 }

public void setChildSalePrice(double childsaleprice) {
ChildSalePrice = childsaleprice;
}

@Element(required=false)
private double SinglePersonCostPrice ;


public double getSinglePersonCostPrice() {
	return SinglePersonCostPrice;
 }

public void setSinglePersonCostPrice(double singlepersoncostprice) {
SinglePersonCostPrice = singlepersoncostprice;
}

@Element(required=false)
private double SinglePersonSalePrice ;


public double getSinglePersonSalePrice() {
	return SinglePersonSalePrice;
 }

public void setSinglePersonSalePrice(double singlepersonsaleprice) {
SinglePersonSalePrice = singlepersonsaleprice;
}

}
