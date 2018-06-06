package com.ss.pojo.ctrip;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class GetTodoOrderListResponse extends ResponseBase {
	 @ElementList(required=false)
	   private List<Order> TodoOrderList;


public List<Order> getTodoOrderList() {
	return TodoOrderList;
 }

public void setTodoOrderList(List<Order> todoorderlist) {
TodoOrderList = todoorderlist;
}
	 
	 public String GetOrderId()
	 {
		 return this.TodoOrderList.get(0).GetOrderId();
	 }
	 
	 public String GetErrorCode()
	 {
		 return super.GetErrorCode();
	 }
}
