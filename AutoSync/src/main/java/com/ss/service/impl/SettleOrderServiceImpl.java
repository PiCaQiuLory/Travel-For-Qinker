package com.ss.service.impl;

import com.ss.mapper.SettleOrderMapper;
import com.ss.pojo.SettleOrder;
import com.ss.service.SettleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * 对账模块业务处理层
 * @author lory
 * @description TODO
 * @Package com.ss.service.impl
 * @date 2018年2月24日
 */
@Service
public class SettleOrderServiceImpl implements SettleOrderService{

	@Autowired
	private SettleOrderMapper orderMapper;
	
	@Override
	public List<SettleOrder> getAllSettleOrder() {
		return orderMapper.getAllSettleOrder();
	}

	@Override
	public Integer getLengthByItemNumber(Integer orderId) {
		return orderMapper.getLengthByItemNumber(orderId);
	}

	@Override
	public List<SettleOrder> getAllSettleOrderWithEnd(Date startDate,Date endDate) {
		return orderMapper.getAllSettleOrderWithEnd(startDate,endDate);
	}
	
}
