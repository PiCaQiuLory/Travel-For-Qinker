package com.ss.service.impl;

import com.ss.mapper.OrderResourceMapper;
import com.ss.pojo.OrderResource;
import com.ss.service.OrderResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderResourceServiceImpl implements OrderResourceService{

	@Autowired
	private OrderResourceMapper resourceMapper;
	
	@Override
	public List<OrderResource> getAllWithNoJudy(OrderResource resource) {
		return resourceMapper.getAllWithNoJudy(resource);
	}

	@Override
	public void applyOneResource(OrderResource resource) {
		resourceMapper.applyOneResource(resource);
	}

	@Override
	public void judyOrderResouceToPay(OrderResource resource) {
		resourceMapper.judyOrderResouceToPay(resource);
	}

	@Override
	public List<OrderResource> search(OrderResource resource) {
		return resourceMapper.search(resource);
	}

	@Override
	public void updateOrderResourceBill(OrderResource resource) {
		resourceMapper.updateOrderResourceBill(resource);
	}



}
