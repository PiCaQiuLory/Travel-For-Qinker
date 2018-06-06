package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Root
public class PackageInventoryInfo {
@Element(required=false)
private String VendorPackageCode ;


public String getVendorPackageCode() {
	return VendorPackageCode;
 }

public void setVendorPackageCode(String vendorpackagecode) {
VendorPackageCode = vendorpackagecode;
}

@Element(required=false)
private String InventoryType ;


public String getInventoryType() {
	return InventoryType;
 }

public void setInventoryType(String inventorytype) {
InventoryType = inventorytype;
}

@Element(required=false)
private int ReservedInventoryQuantity ;


public int getReservedInventoryQuantity() {
	return ReservedInventoryQuantity;
 }

public void setReservedInventoryQuantity(int reservedinventoryquantity) {
ReservedInventoryQuantity = reservedinventoryquantity;
}

@Element(required=false)
private int SharedInventoryQuantity ;


public int getSharedInventoryQuantity() {
	return SharedInventoryQuantity;
 }

public void setSharedInventoryQuantity(int sharedinventoryquantity) {
SharedInventoryQuantity = sharedinventoryquantity;
}

@Element(required=false)
private int ReservedInventoryCleanUpHour ;


public int getReservedInventoryCleanUpHour() {
	return ReservedInventoryCleanUpHour;
 }

public void setReservedInventoryCleanUpHour(int reservedinventorycleanuphour) {
ReservedInventoryCleanUpHour = reservedinventorycleanuphour;
}

@Element(required=false)
private int ReservedInventoryCleanUpDays ;


public int getReservedInventoryCleanUpDays() {
	return ReservedInventoryCleanUpDays;
 }

public void setReservedInventoryCleanUpDays(int reservedinventorycleanupdays) {
ReservedInventoryCleanUpDays = reservedinventorycleanupdays;
}

}
