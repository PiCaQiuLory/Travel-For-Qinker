package com.ss.pojo.vo;

import java.io.Serializable;

public class OrderApplyItemTo implements Serializable{

	private OrderItemVo itemvo;
	
	private int orderMergeId;

	public OrderItemVo getItemvo() {
		return itemvo;
	}

	public void setItemvo(OrderItemVo itemvo) {
		this.itemvo = itemvo;
	}

	public int getOrderMergeId() {
		return orderMergeId;
	}

	public void setOrderMergeId(int orderMergeId) {
		this.orderMergeId = orderMergeId;
	}
	
	
}
