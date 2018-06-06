package com.ss.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ss.pojo.SettleOrder;

public interface SettleOrderMapper {

	public List<SettleOrder> getAllSettleOrder();
	
	public List<SettleOrder> getAllSettleOrderWithEnd(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	public Integer getLengthByItemNumber(Integer orderId);
	
	public List<SettleOrder> getOrderByTime(Date startDate);
	
}
