package com.ss.mapper;

import java.util.List;

import com.ss.pojo.OrderResource;

public interface OrderResourceMapper {
	
	List<OrderResource> getAllWithNoJudy(OrderResource resource);
	
	void applyOneResource(OrderResource resource);
	
	void judyOrderResouceToPay(OrderResource resource);
	
	List<OrderResource> search(OrderResource resource);
	
	void updateOrderResourceBill(OrderResource resource);
}
