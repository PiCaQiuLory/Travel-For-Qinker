package com.ss.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ss.mapper.OrderPlatFormDao;
import com.ss.mapper.SettleOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pojo.SettleOrder;
import com.ss.pojo.trip.OrderPlatform;
import com.ss.service.OrderPlatFormService;

@Service
public class OrderPlatFormServiceImpl implements OrderPlatFormService{
	
	@Autowired
	private SettleOrderMapper settleOrderMapper;
	
	@Autowired
	private OrderPlatFormDao orderPlatForDao;

	@Override
	public List<OrderPlatform> getPlatFormOrder() {
		String s = "2018-03-31";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			 startDate = format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<SettleOrder> settleOrders = new ArrayList<>();
		if(startDate != null) {
			settleOrders = settleOrderMapper.getOrderByTime(startDate);
		}
		List<OrderPlatform> orderPlatforms = orderPlatForDao.getPlatFormOrder();
		Map<String, SettleOrder> orderMap = new HashMap<>();
		for(SettleOrder order:settleOrders) {
			orderMap.put(order.getItemNumber().trim(), order);
		}
		List<OrderPlatform> resultOrder = new ArrayList<>();
		for(OrderPlatform order:orderPlatforms) {
			if(orderMap.get(order.getItemNumber().trim())==null) {
				resultOrder.add(order);
			}
		}
		return resultOrder;
	}

}
