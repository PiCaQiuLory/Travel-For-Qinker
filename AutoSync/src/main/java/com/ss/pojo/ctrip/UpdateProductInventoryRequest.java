package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "UpdateProductInventoryRequest")
public class UpdateProductInventoryRequest  extends RequestBase  {
@Element(required=false)
private String VendorProductCode ;


public String getVendorProductCode() {
	return VendorProductCode;
 }

public void setVendorProductCode(String vendorproductcode) {
VendorProductCode = vendorproductcode;
}

@ElementList(required=false, entry="Inventory")
private List<Inventory> InventoryList ;


public List<Inventory> getInventoryList() {
	return InventoryList;
 }

public void setInventoryList(List<Inventory> inventorylist) {
InventoryList = inventorylist;
}

}
